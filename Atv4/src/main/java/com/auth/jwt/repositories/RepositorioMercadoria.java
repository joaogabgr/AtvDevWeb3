package com.auth.jwt.repositories;

import com.auth.jwt.model.Mercadoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMercadoria extends JpaRepository<Mercadoria, Long> {
}
