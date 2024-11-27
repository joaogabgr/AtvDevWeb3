package com.auth.sistema.service;

import com.auth.sistema.model.Veiculo;
import com.auth.sistema.repositories.RepositorioVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    public Veiculo buscarVeiculo(Long id) {
        try {
            Veiculo veiculo = repositorioVeiculo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
            return veiculo;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar veículo: " + e.getMessage());
        }
    }
}
