package com.upb.fourwheelsdrive.service.implementation;

import com.upb.fourwheelsdrive.service.EmailSender;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailService implements EmailSender {

    private final JavaMailSender javaMailSender;
    @Override
    @Async
    public void sendEmailActivation(String destination, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message, true);
            helper.setTo(destination);
            helper.setSubject("[4 Wheels Drive] Activate account");

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send the email...", e);
            throw new IllegalStateException("Failed to send the email...");
        }
    }

    @Override
    @Async
    public void sendResetPasswordEmail(String destination, String message) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(message, true);
            helper.setTo(destination);
            helper.setSubject("[4 Wheels Drive] Reset your password");

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            log.error("Failed to send the email...", e);
            throw new IllegalStateException("Failed to send the mail...");
        }
    }
}
