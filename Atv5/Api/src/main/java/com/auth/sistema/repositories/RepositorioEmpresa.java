package com.auth.sistema.repositories;

import com.auth.sistema.model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioEmpresa extends JpaRepository<Empresa, Long> {
}