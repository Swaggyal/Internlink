package com.Internlink.backend.service;

import com.Internlink.backend.dto.AuthResponse;
import com.Internlink.backend.dto.LoginRequest;
import com.Internlink.backend.dto.RegisterRequest;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.entity.User;
import com.Internlink.backend.entity.UserRole;
import com.Internlink.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    // REGISTER
    public AuthResponse register(RegisterRequest request) {
        if (request.getEmail() == null || request.getPassword() == null || request.getRole() == null) {
            return new AuthResponse(null, null, request.getRole(), "Missing required fields", false);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, null, request.getRole(), "Email already registered", false);
        }
        try {
            User user;

            // If registering as STUDENT, create a Student entity instead of User
            if (request.getRole() == UserRole.STUDENT) {
                user = new Student();
                ((Student) user).setProfileCompleted(false);
                ((Student) user).setProfileStrength(0);
            } else {
                user = new User();
            }

            user.setEmail(request.getEmail());
            user.setPassword(request.getPassword());
            user.setRole(request.getRole());
            User savedUser = userRepository.save(user);
            return new AuthResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getRole(), "Registration successful", true);
        } catch (Exception e) {
            return new AuthResponse(null, null, request.getRole(), "Server error: " + e.getMessage(), false);
        }
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        if (request.getEmail() == null || request.getPassword() == null) {
            return new AuthResponse(null, null, null, "Missing email or password", false);
        }

        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return new AuthResponse(null, null, null, "User not found", false);
        }

        User user = userOptional.get();

        // Verify password (plain text comparison)
        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(null, null, null, "Invalid password", false);
        }

        // Login successful
        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                "Login successful",
                true
        );
    }

    //Forgot Password
    public String forgotPassword(String email){

        if (!userRepository.existsByEmail(email)) {
            return "Email not found";
        }
        return "Reset link sent successfully";
    }

    //Email Verification
    public String verifyEmail(String email, String otp) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!otp.equals(user.getOtpCode())) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setEmailVerified(true);
        user.setOtpCode(null);

        userRepository.save(user);

        return "Email verified successfully";
    }
}