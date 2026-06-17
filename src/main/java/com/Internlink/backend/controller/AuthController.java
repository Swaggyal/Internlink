package com.Internlink.backend.controller;

import com.Internlink.backend.dto.ForgotPasswordRequest;
import com.Internlink.backend.dto.LoginRequest;
import com.Internlink.backend.dto.AuthResponse;
import com.Internlink.backend.dto.RegisterRequest;
import com.Internlink.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    // Register endpoint
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);

        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);

        if (response.isSuccess()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    //Forgot Password endpoint
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {

        return ResponseEntity.ok(
                authService.forgotPassword(request.getEmail())
        );
    }

    //Email verification endpoint
    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestBody VerifyEmailRequest request) {
        return ResponseEntity.ok(
                authService.verifyEmail(
                        request.getEmail(),
                        request.getOtp()
                )
        );
    }
}