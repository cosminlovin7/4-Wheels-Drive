package com.upb.fourwheelsdrive.model.authenticate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticateRequest {
    private String username;
    private String password;
}
