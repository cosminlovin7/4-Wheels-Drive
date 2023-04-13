package com.upb.fourwheelsdrive.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException {
    private final HttpStatus status;
    private final String message;

    public BaseException(final String message, final HttpStatus status) {
        this.status = status;
        this.message = message;
    }
}
