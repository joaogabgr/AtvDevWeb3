package com.autobots.automanager.services;

import com.autobots.automanager.controles.TelefoneControle;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class TelefoneServices {

    @Autowired
    private TelefoneRepositorio repositorio;

    public void cadastrarTelefone(Telefone data) {
        try {
            repositorio.save(data);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar telefone");
        }
    }

    public Telefone atualizarTelefone(Telefone data) {
        try {
            Telefone telefone = repositorio.findById(data.getId()).orElseThrow(
                    () -> new RuntimeException("Telefone não encontrado")
            );
            telefone.setDdd(data.getDdd());
            telefone.setNumero(data.getNumero());
            repositorio.save(telefone);
            return telefone;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar telefone");
        }
    }

    public void deletarTelefone(Long id) {
        try {
            Telefone telefone = repositorio.findById(id).orElseThrow(
                    () -> new RuntimeException("Telefone não encontrado")
            );
            repositorio.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar telefone");
        }
    }

    public Telefone buscarTelefone(Long id) {
        try {
            Telefone telefone = repositorio.findById(id).orElseThrow(
                    () -> new RuntimeException("Telefone não encontrado")
            );
            telefone.add(
                    linkTo(methodOn(TelefoneControle.class).buscarTelefone(telefone.getId())).withSelfRel().withType("GET"),
                    linkTo(methodOn(TelefoneControle.class).listarTelefones()).withRel("telefones").withType("GET")
            );
            return telefone;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar telefone");
        }
    }

    public List<Telefone> buscarTelefones() {
        try {
            List<Telefone> telefones = repositorio.findAll();
            for (Telefone telefone : telefones) {
                telefone.add(
                        linkTo(methodOn(TelefoneControle.class).buscarTelefone(telefone.getId())).withSelfRel().withType("GET"),
                        linkTo(methodOn(TelefoneControle.class).listarTelefones()).withRel("telefones").withType("GET")
                );
            }
            return telefones;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar telefones");
        }
    }
}
