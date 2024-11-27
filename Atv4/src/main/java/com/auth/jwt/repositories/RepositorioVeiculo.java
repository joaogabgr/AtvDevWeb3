package com.auth.jwt.repositories;

import com.auth.jwt.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioVeiculo extends JpaRepository<Veiculo, Long> {
}
