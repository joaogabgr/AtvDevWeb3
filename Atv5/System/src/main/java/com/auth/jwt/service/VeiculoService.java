package com.auth.jwt.service;

import com.auth.jwt.model.Veiculo;
import com.auth.jwt.repositories.RepositorioVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    @Autowired
    private RepositorioVeiculo repositorioVeiculo;

    public Veiculo criarVeiculo(Veiculo veiculo) {
        try {
            return repositorioVeiculo.save(veiculo);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar veículo: " + e.getMessage());
        }
    }

    public Veiculo atualizarVeiculo(Long id, Veiculo veiculo) {
        try {
            Veiculo veiculoAtualizado = repositorioVeiculo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado para atualização"));

            mapVeiculoParaAtualizar(veiculoAtualizado, veiculo);
            return repositorioVeiculo.save(veiculoAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    public void deletarVeiculo(Long id) {
        try {
            if (!repositorioVeiculo.existsById(id)) {
                throw new RuntimeException("Veículo não encontrado para deleção");
            }
            repositorioVeiculo.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar veículo: " + e.getMessage());
        }
    }

    public Veiculo buscarVeiculo(Long id) {
        try {
            Veiculo veiculo = repositorioVeiculo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
            return veiculo;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar veículo: " + e.getMessage());
        }
    }

    public List<Veiculo> buscarVeiculos() {
        try {
            return repositorioVeiculo.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar veículos: " + e.getMessage());
        }
    }

    private void mapVeiculoParaAtualizar(Veiculo veiculoAtualizado, Veiculo veiculo) {
        veiculoAtualizado.setModelo(veiculo.getModelo());
        veiculoAtualizado.setPlaca(veiculo.getPlaca());
        veiculoAtualizado.setTipo(veiculo.getTipo());
        veiculoAtualizado.setProprietario(veiculo.getProprietario());
        veiculoAtualizado.setVendas(veiculo.getVendas());
    }
}
