package com.upb.fourwheelsdrive.exceptions.authenticate;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class InvalidAuthenticationTokenException extends BaseException {
    public InvalidAuthenticationTokenException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
