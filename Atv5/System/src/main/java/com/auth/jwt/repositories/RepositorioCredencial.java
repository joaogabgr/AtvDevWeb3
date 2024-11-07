package com.auth.jwt.repositories;

import com.auth.jwt.model.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCredencial extends JpaRepository<Credencial, Long> {
}
