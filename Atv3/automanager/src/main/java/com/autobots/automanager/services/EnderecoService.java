package com.autobots.automanager.services;

import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.repositorios.RepositorioEndereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {
    @Autowired
    private RepositorioEndereco repositorioEndereco;

    public Endereco criarEndereco(Endereco endereco) {
        try {
            return repositorioEndereco.save(endereco);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar endereço: " + e.getMessage());
        }
    }

    public Endereco atualizarEndereco(Long id, Endereco endereco) {
        try {
            Endereco enderecoExistente = repositorioEndereco.findById(id)
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
            mapEnderecoParaAtualizar(enderecoExistente, endereco);
            return repositorioEndereco.save(enderecoExistente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar endereço: " + e.getMessage());
        }
    }

    public void deletarEndereco(Long id) {
        try {
            repositorioEndereco.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar endereço: " + e.getMessage());
        }
    }

    public Endereco buscarEndereco(Long id) {
        try {
            Endereco endereco = repositorioEndereco.findById(id)
                    .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
            return endereco;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereço: " + e.getMessage());
        }
    }

    public List<Endereco> buscarEnderecos() {
        try {
            return repositorioEndereco.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereços: " + e.getMessage());
        }
    }

    private void mapEnderecoParaAtualizar(Endereco enderecoExistente, Endereco novoEndereco) {
        if (novoEndereco.getEstado() != null) {
            enderecoExistente.setEstado(novoEndereco.getEstado());
        }
        if (novoEndereco.getCidade() != null) {
            enderecoExistente.setCidade(novoEndereco.getCidade());
        }
        if (novoEndereco.getBairro() != null) {
            enderecoExistente.setBairro(novoEndereco.getBairro());
        }
        if (novoEndereco.getRua() != null) {
            enderecoExistente.setRua(novoEndereco.getRua());
        }
        if (novoEndereco.getNumero() != null) {
            enderecoExistente.setNumero(novoEndereco.getNumero());
        }
        if (novoEndereco.getCodigoPostal() != null) {
            enderecoExistente.setCodigoPostal(novoEndereco.getCodigoPostal());
        }
        if (novoEndereco.getInformacoesAdicionais() != null) {
            enderecoExistente.setInformacoesAdicionais(novoEndereco.getInformacoesAdicionais());
        }
    }
}
