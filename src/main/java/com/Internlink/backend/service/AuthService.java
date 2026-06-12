package com.Internlink.backend.service;

import com.Internlink.backend.dto.LoginRequest;
import com.Internlink.backend.dto.AuthResponse;
import com.Internlink.backend.dto.RegisterRequest;
import com.Internlink.backend.entity.User;
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

    // Register new user
    public AuthResponse register(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse(null, null, null, null, "Email already registered", false);
        }

        // Check if phone already exists
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            return new AuthResponse(null, null, null, null, "Phone number already registered", false);
        }

        // Create new user
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Plain text for now
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getPhoneNumber(),
                savedUser.getRole(),
                "Registration successful",
                true
        );
    }

    // Login user
    public AuthResponse login(LoginRequest request) {
        // Find user by email
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isEmpty()) {
            return new AuthResponse(null, null, null, null, "User not found", false);
        }

        User user = userOptional.get();

        // Verify password (plain text comparison for now)
        if (!user.getPassword().equals(request.getPassword())) {
            return new AuthResponse(null, null, null, null, "Invalid password", false);
        }

        // Login successful
        return new AuthResponse(
                user.getId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole(),
                "Login successful",
                true
        );
    }
}