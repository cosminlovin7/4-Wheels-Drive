package com.upb.fourwheelsdrive.service;

import com.upb.fourwheelsdrive.model.register.RegisterRequest;

public interface RegisterService {
    String register(RegisterRequest request);
    String activateAccount(String username, String token);
    String resendActivationLink(String username);
}
