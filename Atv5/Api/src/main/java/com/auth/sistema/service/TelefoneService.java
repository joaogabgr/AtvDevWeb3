package com.auth.sistema.service;

import com.auth.sistema.model.Telefone;
import com.auth.sistema.repositories.RepositorioTelefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelefoneService {

    @Autowired
    private RepositorioTelefone repositorioTelefone;

    public Telefone criarTelefone(Telefone telefone) {
        try {
            return repositorioTelefone.save(telefone);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar telefone: " + e.getMessage());
        }
    }

    public Telefone atualizarTelefone(Long id, Telefone telefone) {
        try {
            Telefone telefoneAtualizado = repositorioTelefone.findById(id)
                    .orElseThrow(() -> new RuntimeException("Telefone não encontrado para atualização"));

            mapTelefoneParaAtualizar(telefoneAtualizado, telefone);
            return repositorioTelefone.save(telefoneAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar telefone: " + e.getMessage());
        }
    }

    public void deletarTelefone(Long id) {
        try {
            if (!repositorioTelefone.existsById(id)) {
                throw new RuntimeException("Telefone não encontrado para deleção");
            }
            repositorioTelefone.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar telefone: " + e.getMessage());
        }
    }

    public Telefone buscarTelefone(Long id) {
        try {
            Telefone telefone = (Telefone) repositorioTelefone.findById(id)
                    .orElseThrow(() -> new RuntimeException("Telefone não encontrado"));
            return telefone;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar telefone: " + e.getMessage());
        }
    }

    public List<Telefone> buscarTelefones() {
        try {
            return repositorioTelefone.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar telefones: " + e.getMessage());
        }
    }

    private void mapTelefoneParaAtualizar(Telefone telefoneAtualizado, Telefone telefone) {
        telefoneAtualizado.setDdd(telefone.getDdd());
        telefoneAtualizado.setNumero(telefone.getNumero());
    }

}
