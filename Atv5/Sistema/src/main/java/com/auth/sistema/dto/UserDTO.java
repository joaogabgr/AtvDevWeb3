package com.auth.sistema.dto;

import com.auth.sistema.enums.UserRole;

public record UserDTO(String name, String email, UserRole role) {
}
