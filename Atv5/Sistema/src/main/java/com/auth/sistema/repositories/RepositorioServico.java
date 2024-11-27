package com.auth.sistema.repositories;

import com.auth.sistema.model.Empresa;
import com.auth.sistema.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepositorioServico extends JpaRepository<Servico, Long> {
    List<Servico> findByEmpresa(Empresa empresa);
    List<Servico> findByEmpresaId(Long id);
}
