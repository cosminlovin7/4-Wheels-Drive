package com.upb.fourwheelsdrive.exceptions.register;

import com.upb.fourwheelsdrive.exceptions.BaseException;
import org.springframework.http.HttpStatus;

public class UsernameNotFoundException extends BaseException {
    public UsernameNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
