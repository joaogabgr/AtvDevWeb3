package com.auth.jwt.repositories;

import com.auth.jwt.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioEndereco extends JpaRepository<Endereco, Long> {
}
