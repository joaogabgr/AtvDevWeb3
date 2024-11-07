package com.auth.jwt.dto;

import com.auth.jwt.enums.UserRole;

public record UserDTO(String name, String email, UserRole role) {
}
