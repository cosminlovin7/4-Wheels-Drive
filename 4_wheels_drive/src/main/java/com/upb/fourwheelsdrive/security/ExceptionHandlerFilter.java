package com.upb.fourwheelsdrive.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upb.fourwheelsdrive.model.exception.ExceptionResponse;
import com.upb.fourwheelsdrive.utils.Constants;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@AllArgsConstructor
@Component
@Slf4j
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.error(Constants.INVALID_AUTHENTICATION_TOKEN);
            ExceptionResponse exceptionResponse = new ExceptionResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    Constants.INVALID_AUTHENTICATION_TOKEN,
                    LocalDateTime.now());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(exceptionResponse));
        }
    }
}
