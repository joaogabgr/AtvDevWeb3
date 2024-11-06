package com.auth.jwt.repositories;

import com.auth.jwt.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioTelefone extends JpaRepository<Telefone, Long> {
}
