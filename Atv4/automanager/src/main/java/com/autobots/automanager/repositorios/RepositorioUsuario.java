package com.autobots.automanager.repositorios;

import com.autobots.automanager.entitades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
}
