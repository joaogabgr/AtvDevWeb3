package com.auth.sistema.service;

import com.auth.sistema.controller.*;
import com.auth.sistema.enums.UserRole;
import com.auth.sistema.model.*;
import com.auth.sistema.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class Atv5Services {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RepositorioServico repositorioServico;

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    @Autowired
    private RepositorioVenda repositorioVenda;

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    public List<Usuario> listClienteForEmpresa(Long idEmpresa) {
        try {
            return userRepository.findByEmpresaIdAndRole(idEmpresa, UserRole.CLIENTE);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários da empresa");
        }
    }

    public List<Usuario> listFuncionarioForEmpresa(Long idEmpresa) {
        try {
            return userRepository.findByEmpresaIdAndRoleNot(idEmpresa, UserRole.CLIENTE);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários da empresa");
        }
    }

    public List<Mercadoria> listMercadoriaForEmpresa(Long idEmpresa) {
        try {
            List<Mercadoria> mercadorias = repositorioMercadoria.findByEmpresaId(idEmpresa);
            return mercadorias;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar mercadorias da empresa");
        }
    }

    public List<Servico> listServicoForEmpresa(Long idEmpresa) {
        try {
            List<Servico> servicos = repositorioServico.findByEmpresaId(idEmpresa);
            return servicos;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar serviços da empresa");
        }
    }


    public List<Venda> listVendaPorEmpresaEData(Long idEmpresa, Date startDate, Date endDate) {
        try {
            List<Venda> vendas = repositorioVenda.findByEmpresaIdAndCadastroBetween(idEmpresa, startDate, endDate);
            return vendas;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar mercadorias da empresa");
        }
    }

    public List<Veiculo> listVeiculoForEmpresa(Long idEmpresa) {
        try {
            List<Veiculo> veiculos = repositorioVeiculo.findByEmpresaId(idEmpresa);
            return veiculos;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar veículos da empresa");
        }
    }
}
