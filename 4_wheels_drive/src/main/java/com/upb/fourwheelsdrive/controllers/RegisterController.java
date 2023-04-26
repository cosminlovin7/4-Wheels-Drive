package com.upb.fourwheelsdrive.controllers;

import com.upb.fourwheelsdrive.model.BaseResponse;
import com.upb.fourwheelsdrive.model.register.RegisterRequest;
import com.upb.fourwheelsdrive.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class RegisterController {

    private RegisterService registerService;

    @PostMapping
    public ResponseEntity<BaseResponse> register(@RequestBody RegisterRequest request) {
        String registerMessage = registerService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                new BaseResponse(HttpStatus.CREATED.value(), registerMessage)
        );
    }

    @GetMapping("/confirm")
    public ResponseEntity<BaseResponse> confirm(@RequestParam("username") String username, @RequestParam("token") String token) {
        String activateAccountMessage = registerService.activateAccount(username, token);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse(HttpStatus.OK.value(), activateAccountMessage)
        );
    }

    @GetMapping("/resend")
    public ResponseEntity<BaseResponse> resendActivationLink(@RequestParam("username") String username) {
        String resendActivationLinkMessage = registerService.resendActivationLink(username);

        return ResponseEntity.status(HttpStatus.OK).body(
                new BaseResponse(HttpStatus.OK.value(), resendActivationLinkMessage)
        );
    }
}
