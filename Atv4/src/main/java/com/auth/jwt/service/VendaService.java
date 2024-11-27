package com.auth.jwt.service;

import com.auth.jwt.model.Venda;
import com.auth.jwt.repositories.RepositorioVenda;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService {

    @Autowired
    private RepositorioVenda repositorioVenda;

    public Venda criarVenda(Venda venda) {
        try {
            return repositorioVenda.save(venda);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar venda: " + e.getMessage());
        }
    }

    public Venda atualizarVenda(Long id, Venda venda) {
        try {
            Venda vendaAtualizada = repositorioVenda.findById(id)
                    .orElseThrow(() -> new RuntimeException("Venda não encontrada para atualização"));

            mapVendaParaAtualizar(vendaAtualizada, venda);
            return repositorioVenda.save(vendaAtualizada);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar venda: " + e.getMessage());
        }
    }

    public void deletarVenda(Long id) {
        try {
            if (!repositorioVenda.existsById(id)) {
                throw new RuntimeException("Venda não encontrada para deleção");
            }
            repositorioVenda.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar venda: " + e.getMessage());
        }
    }

    public Venda buscarVenda(Long id) {
        try {
            Venda venda = repositorioVenda.findById(id)
                    .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
            return venda;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar venda: " + e.getMessage());
        }
    }

    public List<Venda> buscarVendas() {
        try {
            return repositorioVenda.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar vendas: " + e.getMessage());
        }
    }

    private void mapVendaParaAtualizar(Venda vendaAtualizada, Venda venda) {
        vendaAtualizada.setCadastro(venda.getCadastro());
        vendaAtualizada.setFuncionario(venda.getFuncionario());
        vendaAtualizada.setCliente(venda.getCliente());
        vendaAtualizada.setVeiculo(venda.getVeiculo());
        vendaAtualizada.setMercadorias(venda.getMercadorias());
        vendaAtualizada.setIdentificacao(venda.getIdentificacao());
        vendaAtualizada.setServicos(venda.getServicos());
    }
}
