package com.auth.jwt.service;

import com.auth.jwt.model.Servico;
import com.auth.jwt.repositories.RepositorioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    @Autowired
    private RepositorioServico repositorioServico;

    public Servico criarServico(Servico servico) {
        try {
            return repositorioServico.save(servico);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar serviço: " + e.getMessage());
        }
    }

    public Servico atualizarServico(Long id, Servico servico) {
        try {
            Servico servicoAtualizado = repositorioServico.findById(id)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
            mapServicoParaAtualizacao(servico, servicoAtualizado);
            return repositorioServico.save(servicoAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar serviço: " + e.getMessage());
        }
    }

    public void deletarServico(Long id) {
        try {
            if (!repositorioServico.existsById(id)) {
                throw new RuntimeException("Serviço não encontrado para deleção");
            }
            repositorioServico.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar serviço: " + e.getMessage());
        }
    }

    public Servico buscarServico(Long id) {
        try {
            Servico servico = repositorioServico.findById(id)
                    .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
            return servico;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar serviço: " + e.getMessage());
        }
    }

    public List<Servico> buscarServicos() {
        try {
            return repositorioServico.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar serviços: " + e.getMessage());
        }
    }

    private void mapServicoParaAtualizacao(Servico servico, Servico servicoAtualizado) {
        servicoAtualizado.setNome(servico.getNome());
        servicoAtualizado.setDescricao(servico.getDescricao());
        servicoAtualizado.setValor(servico.getValor());
    }
}
