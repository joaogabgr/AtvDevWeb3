package com.auth.jwt.repositories;

import com.auth.jwt.model.Empresa;
import com.auth.jwt.model.Mercadoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RepositorioMercadoria extends JpaRepository<Mercadoria, Long> {
    List<Mercadoria> findByEmpresa(Empresa empresa);
    List<Mercadoria> findByEmpresaId(Long id);
}
