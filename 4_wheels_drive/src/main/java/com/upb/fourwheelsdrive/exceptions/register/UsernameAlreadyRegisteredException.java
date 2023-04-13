package com.upb.fourwheelsdrive.exceptions.register;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UsernameAlreadyRegisteredException extends BaseException {
    public UsernameAlreadyRegisteredException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
