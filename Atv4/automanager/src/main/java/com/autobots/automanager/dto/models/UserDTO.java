package com.autobots.automanager.dto.models;

import com.autobots.automanager.enumeracoes.UserRole;

public record UserDTO(String name, String email, UserRole role) {
}

