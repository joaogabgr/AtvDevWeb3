package com.auth.sistema.service;

import com.auth.sistema.model.Venda;
import com.auth.sistema.repositories.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {

    @Autowired
    private RepositorioVenda repositorioVenda;

    public Venda buscarVenda(Long id) {
        try {
            Venda venda = repositorioVenda.findById(id)
                    .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
            return venda;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar venda: " + e.getMessage());
        }
    }
}
