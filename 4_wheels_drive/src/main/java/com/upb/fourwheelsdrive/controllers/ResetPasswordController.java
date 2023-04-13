package com.upb.fourwheelsdrive.controllers;

import com.upb.fourwheelsdrive.model.BaseResponse;
import com.upb.fourwheelsdrive.model.reset_password.ResetPasswordRequest;
import com.upb.fourwheelsdrive.service.ResetPasswordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reset-password")
@AllArgsConstructor
public class ResetPasswordController {

    private final ResetPasswordService resetPasswordService;

    @GetMapping
    public ResponseEntity<BaseResponse> handleResetPasswordRequest(@RequestParam("username") String username) {
        String resetPasswordRequestMessage = resetPasswordService.handleResetPasswordRequest(username);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse(HttpStatus.OK.value(), resetPasswordRequestMessage)
        );
    }

    @PostMapping("/reset")
    public ResponseEntity<BaseResponse> resetPassword(
            @RequestBody ResetPasswordRequest resetPasswordRequest,
            @RequestParam("token")String token,
            @RequestParam("username")String username) {
        String resetPasswordMessage = resetPasswordService.resetPassword(token, username, resetPasswordRequest);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse(HttpStatus.OK.value(), resetPasswordMessage)
        );
    }
}
