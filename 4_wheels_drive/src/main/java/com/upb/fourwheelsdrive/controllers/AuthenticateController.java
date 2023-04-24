package com.upb.fourwheelsdrive.controllers;

import com.upb.fourwheelsdrive.model.authenticate.AuthenticateRequest;
import com.upb.fourwheelsdrive.model.authenticate.AuthenticateResponse;
import com.upb.fourwheelsdrive.service.AuthenticateService;
import com.upb.fourwheelsdrive.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/login")
@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
public class AuthenticateController {

    private final AuthenticateService service;

    @PostMapping()
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request) {
        String token = service.authenticate(request);

        return ResponseEntity.status(HttpStatus.OK).body(new AuthenticateResponse(
                HttpStatus.OK.value(),
                Constants.AUTHENTICATION_SUCCESSFUL,
                token)
        );
    }
}
