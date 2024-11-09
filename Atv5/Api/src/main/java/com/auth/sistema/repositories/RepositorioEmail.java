package com.auth.sistema.repositories;

import com.auth.sistema.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioEmail extends JpaRepository<Email, Long> {
}
