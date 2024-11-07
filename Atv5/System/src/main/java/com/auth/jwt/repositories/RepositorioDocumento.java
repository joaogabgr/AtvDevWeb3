package com.auth.jwt.repositories;

import com.auth.jwt.model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioDocumento extends JpaRepository<Documento, Long> {
    //public Documento findByRazaoSocial(String nome);
}
