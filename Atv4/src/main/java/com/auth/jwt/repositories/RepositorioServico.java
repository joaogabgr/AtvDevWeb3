package com.auth.jwt.repositories;

import com.auth.jwt.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioServico extends JpaRepository<Servico, Long> {
}
