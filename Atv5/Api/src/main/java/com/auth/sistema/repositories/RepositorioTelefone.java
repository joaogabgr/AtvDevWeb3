package com.auth.sistema.repositories;

import com.auth.sistema.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioTelefone extends JpaRepository<Telefone, Long> {
}
