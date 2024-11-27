package com.auth.sistema.repositories;

import com.auth.sistema.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioVeiculo extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByEmpresaId(Long id);
}
