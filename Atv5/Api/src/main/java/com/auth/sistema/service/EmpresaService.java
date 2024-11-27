package com.auth.sistema.service;

import com.auth.sistema.model.Empresa;
import com.auth.sistema.repositories.RepositorioEmpresa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class EmpresaService {

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    public Empresa criarEmpresa(Empresa empresa) {
        try {
            return repositorioEmpresa.save(empresa);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar empresa: " + e.getMessage());
        }
    }

    public Empresa atualizarEmpresa(Long id, Empresa empresa) {
        try {
            Empresa empresaAtualizada = repositorioEmpresa.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
            mapEmpresaParaAtualizar(empresaAtualizada, empresa);
            return repositorioEmpresa.save(empresaAtualizada);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar empresa: " + e.getMessage());
        }
    }

    public void deletarEmpresa(Long id) {
        try {
            repositorioEmpresa.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar empresa: " + e.getMessage());
        }
    }

    public Empresa buscarEmpresa(Long id) {
        try {
            Empresa empresa = repositorioEmpresa.findById(id)
                    .orElseThrow(() -> new RuntimeException("Empresa não encontrada"));
            addLinks(EntityModel.of(empresa), id);
            return empresa;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar empresa: " + e.getMessage());
        }
    }

    public List<Empresa> buscarEmpresas() {
        try {
            List<Empresa> empresas = repositorioEmpresa.findAll();
            empresas.forEach(empresa -> addLinks(EntityModel.of(empresa), empresa.getId()));
            return empresas;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar empresas: " + e.getMessage());
        }
    }

    private void addLinks(EntityModel<Empresa> empresaModel, Long id) {
        empresaModel.add(linkTo(methodOn(EmpresaService.class).buscarEmpresa(id)).withSelfRel());
        empresaModel.add(linkTo(methodOn(EmpresaService.class).buscarEmpresas()).withRel("empresas"));
    }

    private void mapEmpresaParaAtualizar(Empresa empresaAtualizada, Empresa novaEmpresa) {
        empresaAtualizada.setRazaoSocial(novaEmpresa.getRazaoSocial());
        empresaAtualizada.setNomeFantasia(novaEmpresa.getNomeFantasia());
        empresaAtualizada.setCadastro(novaEmpresa.getCadastro());
        empresaAtualizada.setEndereco(novaEmpresa.getEndereco());
        empresaAtualizada.setTelefones(novaEmpresa.getTelefones());
        empresaAtualizada.setUsuarios(novaEmpresa.getUsuarios());
        empresaAtualizada.setMercadorias(novaEmpresa.getMercadorias());
        empresaAtualizada.setServicos(novaEmpresa.getServicos());
        empresaAtualizada.setVendas(novaEmpresa.getVendas());
    }
}

