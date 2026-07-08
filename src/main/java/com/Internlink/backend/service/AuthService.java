package com.Internlink.backend.service;

import com.Internlink.backend.dto.AuthResponse;
import com.Internlink.backend.dto.LoginRequest;
import com.Internlink.backend.dto.RegisterRequest;
import com.Internlink.backend.entity.PasswordReset;
import com.Internlink.backend.entity.Student;
import com.Internlink.backend.entity.User;
import com.Internlink.backend.entity.UserRole;
import com.Internlink.backend.repository.PasswordResetRepository;
import com.Internlink.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;

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
            user.setEmailVerified(false);

            // Generate and set OTP
            String otp = generateOTP();
            user.setOtpCode(otp);
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));

            User savedUser = userRepository.save(user);

            // Send OTP email
            //emailService.sendOtpEmail(savedUser.getEmail(), otp);

            return new AuthResponse(savedUser.getId(), savedUser.getEmail(), savedUser.getRole(), "Registration successful. Check email for OTP.", true);
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

        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(null, null, null, "Invalid password", false);
        }

        // Check if email is verified
        if (!user.isEmailVerified()) {
            return new AuthResponse(null, null, null, "Please verify your email first", false);
        }

        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getRole(),
                "Login successful",
                true
        );
    }

    // VERIFY EMAIL
    public String verifyEmail(String email, String otp) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check OTP
        if (!otp.equals(user.getOtpCode())) {
            throw new RuntimeException("Invalid OTP");
        }

        // Check if OTP expired
        if (LocalDateTime.now().isAfter(user.getOtpExpiry())) {
            throw new RuntimeException("OTP expired");
        }

        user.setEmailVerified(true);
        user.setOtpCode(null);
        user.setOtpExpiry(null);

        userRepository.save(user);

        return "Email verified successfully";
    }

    // FORGOT PASSWORD
    public String forgotPassword(String email) {
        if (!userRepository.existsByEmail(email)) {
            return "Email not found";
        }

        // Generate reset token
        String token = UUID.randomUUID().toString();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(15);

        PasswordReset passwordReset = new PasswordReset();
        passwordReset.setEmail(email);
        passwordReset.setToken(token);
        passwordReset.setExpiryTime(expiryTime);
        passwordReset.setUsed(false);

        passwordResetRepository.save(passwordReset);

        // Send reset email
        String resetLink = "http://localhost:3000/reset-password?token=" + token;
        //emailService.sendPasswordResetEmail(email, resetLink);

        return "Password reset link sent to email";
    }

    // RESET PASSWORD
    public String resetPassword(String token, String newPassword) {
        Optional<PasswordReset> resetOpt = passwordResetRepository.findByToken(token);

        if (resetOpt.isEmpty()) {
            throw new RuntimeException("Invalid token");
        }

        PasswordReset passwordReset = resetOpt.get();

        // Check if expired
        if (LocalDateTime.now().isAfter(passwordReset.getExpiryTime())) {
            throw new RuntimeException("Token expired");
        }

        // Check if already used
        if (passwordReset.isUsed()) {
            throw new RuntimeException("Token already used");
        }

        // Update password
        User user = userRepository.findByEmail(passwordReset.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(newPassword);
        userRepository.save(user);

        // Mark token as used
        passwordReset.setUsed(true);
        passwordResetRepository.save(passwordReset);

        return "Password reset successfully";
    }

    // Generate random OTP
    private String generateOTP() {
        return String.format("%06d", (int) (Math.random() * 1000000));
    }
}