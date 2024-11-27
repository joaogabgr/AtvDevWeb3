package com.auth.sistema.repositories;

import com.auth.sistema.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioEndereco extends JpaRepository<Endereco, Long> {
}
