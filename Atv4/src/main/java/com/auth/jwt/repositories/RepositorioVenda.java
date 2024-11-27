package com.auth.jwt.repositories;

import com.auth.jwt.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioVenda extends JpaRepository<Venda, Long> {
}
