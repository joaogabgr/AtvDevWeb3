package com.auth.jwt.service;

import com.auth.jwt.enums.UserRole;
import com.auth.jwt.model.*;
import com.auth.jwt.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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
            return repositorioMercadoria.findByEmpresaId(idEmpresa);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar mercadorias da empresa");
        }
    }

    public List<Servico> listServicoForEmpresa(Long idEmpresa) {
        try {
            return repositorioServico.findByEmpresaId(idEmpresa);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar serviços da empresa");
        }
    }


    public List<Venda> listVendaPorEmpresaEData(Long idEmpresa, Date startDate, Date endDate) {
        try {
            return repositorioVenda.findByEmpresaIdAndCadastroBetween(idEmpresa, startDate, endDate);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar mercadorias da empresa");
        }
    }

    public List<Veiculo> listVeiculoForEmpresa(Long idEmpresa) {
        try {
            return repositorioVeiculo.findByEmpresaId(idEmpresa);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar veículos da empresa");
        }
    }

}
