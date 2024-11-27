package com.auth.sistema.repositories;

import com.auth.sistema.model.Empresa;
import com.auth.sistema.model.Mercadoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioMercadoria extends JpaRepository<Mercadoria, Long> {
    List<Mercadoria> findByEmpresa(Empresa empresa);
    List<Mercadoria> findByEmpresaId(Long id);
}
