package com.Internlink.backend.dto;

import com.Internlink.backend.entity.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String phoneNumber;
    private UserRole role;
}