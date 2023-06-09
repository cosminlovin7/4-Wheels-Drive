package com.upb.fourwheelsdrive.exceptions;

import com.upb.fourwheelsdrive.model.exception.ExceptionResponse;
import com.upb.fourwheelsdrive.utils.Constants;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<ExceptionResponse> handleBaseException(BaseException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                e.getStatus().value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponse, e.getStatus());
    }

    @ExceptionHandler()
    public ResponseEntity<ExceptionResponse> handleSignatureException(SignatureException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Invalid token",
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler()
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
            HttpStatus.BAD_REQUEST.value(),
            Constants.OOPS,
            LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
            BadCredentialsException.class,
            DisabledException.class,
            LockedException.class
    })
    public ResponseEntity<ExceptionResponse> handleAuthenticateException(RuntimeException e) {
        HttpStatus unauthorizedStatus = HttpStatus.UNAUTHORIZED;

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                unauthorizedStatus.value(),
                e.getMessage(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exceptionResponse, unauthorizedStatus);
    }
}
