package com.campusconnect.authservice.controller;

import com.campusconnect.authservice.constant.ResponseBuilder;
import com.campusconnect.authservice.constant.ResponseConstants;
import com.campusconnect.authservice.model.dto.*;
import com.campusconnect.authservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @GetMapping("/healthcheck")
    public String healthCheck() {
        return "OK";
    }


    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> register(@RequestBody @Valid UserDTO userDTO) {
        UserDTO user = authService.saveUser(userDTO);
        return ResponseBuilder.success(ResponseConstants.MESSAGE_201, user);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(@RequestBody @Valid OTPRequest req) {
        return ResponseEntity.ok(authService.verify(req));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> sendOtp(@RequestBody @Valid UserDTO req) {
        UserDTO user = authService.sendOTP(req);
        return ResponseBuilder.success(ResponseConstants.MESSAGE_201, "OTP sent successfully");
    }
}
