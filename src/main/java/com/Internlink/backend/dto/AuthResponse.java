package com.Internlink.backend.dto;

import com.Internlink.backend.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private Long id;
    private String email;
    private UserRole role;
    private String message;
    private boolean success;

    public AuthResponse(Object o, Object o1, Object o2, Object o3, String userNotFound, boolean b) {
    }
}