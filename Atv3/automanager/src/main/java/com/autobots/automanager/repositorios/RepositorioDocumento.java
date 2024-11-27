package com.autobots.automanager.repositorios;

import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.services.DocumentoService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDocumento extends JpaRepository<Documento, Long> {
    //public Documento findByRazaoSocial(String nome);
}
