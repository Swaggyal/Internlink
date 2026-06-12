package com.Internlink.backend.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String email;
    private String password;
    private String phoneNumber;
    private String role;
}