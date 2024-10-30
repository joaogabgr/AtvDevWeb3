package com.autobots.automanager.services;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecosServices {

    @Autowired
    private EnderecoRepositorio repositorio;

    public void cadastrarEndereco(Endereco data) {
        try {
            repositorio.save(data);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar endereço");
        }
    }

    public Endereco atualizarEndereco(Endereco data) {
        try {
            Endereco endereco = repositorio.findById(data.getId()).orElseThrow(
                    () -> new RuntimeException("Endereço não encontrado")
            );
            endereco.setNumero(data.getNumero());
            endereco.setBairro(data.getBairro());
            endereco.setCidade(data.getCidade());
            endereco.setEstado(data.getEstado());
            endereco.setRua(data.getRua());
            endereco.setCodigoPostal(data.getCodigoPostal());
            endereco.setInformacoesAdicionais(data.getInformacoesAdicionais());
            repositorio.save(endereco);
            return endereco;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar endereço");
        }
    }

    public void deletarEndereco(Long id) {
        try {
            Endereco endereco = repositorio.findById(id).orElseThrow(
                    () -> new RuntimeException("Endereço não encontrado")
            );
            repositorio.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar endereço");
        }
    }

    public Endereco buscarEndereco(Long id) {
        try {
            Endereco endereco = repositorio.findById(id).orElseThrow(
                    () -> new RuntimeException("Endereço não encontrado")
            );
            return endereco;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereço");
        }
    }

    public List<Endereco> buscarEnderecos() {
        try {
            List<Endereco> enderecos = repositorio.findAll();
            return enderecos;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereços");
        }
    }

}