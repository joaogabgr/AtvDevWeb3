package com.auth.sistema.service;

import com.auth.sistema.model.Credencial;
import com.auth.sistema.repositories.RepositorioCredencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredencialService {

    @Autowired
    private RepositorioCredencial repositorioCredencial;

    public Credencial criarCredencial(Credencial credencial) {
        try {
            return repositorioCredencial.save(credencial);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar credencial: " + e.getMessage());
        }
    }

    public Credencial atualizarCredencial(Long id, Credencial credencial) {
        try {
            Credencial credencialAtualizado = repositorioCredencial.findById(id)
                    .orElseThrow(() -> new RuntimeException("Credencial não encontrada para atualização"));

            mapCredencialParaAtualizar(credencialAtualizado, credencial);
            return repositorioCredencial.save(credencialAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar credencial: " + e.getMessage());
        }
    }

    public void deletarCredencial(Long id) {
        try {
            if (!repositorioCredencial.existsById(id)) {
                throw new RuntimeException("Credencial não encontrada para deleção");
            }
            repositorioCredencial.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar credencial: " + e.getMessage());
        }
    }

    public Credencial buscarCredencial(Long id) {
        try {
            Credencial credencial = repositorioCredencial.findById(id)
                    .orElseThrow(() -> new RuntimeException("Credencial não encontrada"));
            return credencial;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar credencial: " + e.getMessage());
        }
    }

    public List<Credencial> buscarCredenciais() {
        try {
            return repositorioCredencial.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar credenciais: " + e.getMessage());
        }
    }

    private void mapCredencialParaAtualizar(Credencial credencialAtualizado, Credencial credencial) {
        credencialAtualizado.setCriacao(credencial.getCriacao());
        credencialAtualizado.setUltimoAcesso(credencial.getUltimoAcesso());
        credencialAtualizado.setInativo(credencial.isInativo());
    }
}
