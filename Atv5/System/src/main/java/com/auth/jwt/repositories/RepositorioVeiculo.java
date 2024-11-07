package com.auth.jwt.repositories;

import com.auth.jwt.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioVeiculo extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByEmpresaId(Long id);
}
