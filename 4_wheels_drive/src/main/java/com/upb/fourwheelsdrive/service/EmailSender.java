package com.upb.fourwheelsdrive.service;

public interface EmailSender {
    void sendEmailActivation(final String destination, final String email);
    void sendResetPasswordEmail(final String destination, final String email);
}
