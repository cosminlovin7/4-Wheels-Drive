package com.upb.fourwheelsdrive.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upb.fourwheelsdrive.exceptions.BaseException;
import com.upb.fourwheelsdrive.service.JwtService;
import com.upb.fourwheelsdrive.service.implementation.ApplicationUserServiceImpl;
import com.upb.fourwheelsdrive.utils.Constants;
import com.upb.fourwheelsdrive.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@Slf4j
public class HelloWorld {

    @Value("${jwt.authentication.key}")
    private String key;

    private final ApplicationUserServiceImpl applicationUserService;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @GetMapping("/hello")
    public String testHello() {
        return "hello world!";
    }

    @GetMapping()
    public String testingPage(@NonNull HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalStateException("Invalid or expired token.");
        }

        String jwtToken = authHeader.substring(7);
        String userAuthority;
        try {
            userAuthority = JwtUtils.getAuthorityFromToken(jwtToken);
        } catch (JsonProcessingException e) {
            throw new BaseException(Constants.PARSE_JSON_ERROR, HttpStatus.BAD_REQUEST);
        }

        if (!userAuthority.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
            throw new BaseException(Constants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
        }

        UserDetails applicationUser = applicationUserService
                .loadUserByUsername(jwtService.getUsernameFromToken(jwtToken));

        return "Welcome " + applicationUser.getUsername();
    }
}
