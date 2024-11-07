package com.auth.jwt.repositories;

import com.auth.jwt.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioEmpresa extends JpaRepository<Empresa, Long> {
}