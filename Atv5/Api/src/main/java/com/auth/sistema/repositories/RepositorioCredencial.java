package com.auth.sistema.repositories;

import com.auth.sistema.model.Credencial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCredencial extends JpaRepository<Credencial, Long> {
}
