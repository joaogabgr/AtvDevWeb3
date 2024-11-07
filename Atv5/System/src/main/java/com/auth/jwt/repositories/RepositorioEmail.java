package com.auth.jwt.repositories;

import com.auth.jwt.model.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioEmail extends JpaRepository<Email, Long> {
}
