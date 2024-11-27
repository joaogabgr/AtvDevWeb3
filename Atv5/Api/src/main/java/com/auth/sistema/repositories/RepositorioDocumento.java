package com.auth.sistema.repositories;

import com.auth.sistema.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDocumento extends JpaRepository<Documento, Long> {
    //public Documento findByRazaoSocial(String nome);
}
