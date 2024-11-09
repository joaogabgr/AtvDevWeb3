package com.auth.sistema.service;

import com.auth.sistema.model.Mercadoria;
import com.auth.sistema.repositories.RepositorioMercadoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MercadoriaService {

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    public Mercadoria buscarMercadoria(Long id) {
        try {
            Mercadoria mercadoria = repositorioMercadoria.findById(id)
                    .orElseThrow(() -> new RuntimeException("Mercadoria não encontrada"));
            return mercadoria;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar mercadoria: " + e.getMessage());
        }
    }
}
