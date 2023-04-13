package com.upb.fourwheelsdrive.service;

import com.upb.fourwheelsdrive.model.reset_password.ResetPasswordRequest;

public interface ResetPasswordService {
    String handleResetPasswordRequest(String username);
    String resetPassword(String token, String username, ResetPasswordRequest resetPasswordRequest);
}
