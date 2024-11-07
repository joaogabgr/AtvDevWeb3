package com.auth.jwt.repositories;

import com.auth.jwt.model.Empresa;
import com.auth.jwt.model.Mercadoria;
import com.auth.jwt.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RepositorioServico extends JpaRepository<Servico, Long> {
    List<Servico> findByEmpresa(Empresa empresa);
    List<Servico> findByEmpresaId(Long id);
}
