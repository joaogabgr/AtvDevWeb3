package com.autobots.automanager.services;

import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.repositorios.RepositorioMercadoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MercadoriaService {

    @Autowired
    private RepositorioMercadoria repositorioMercadoria;

    public Mercadoria criarMercadoria(Mercadoria mercadoria) {
        try {
            return repositorioMercadoria.save(mercadoria);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar mercadoria: " + e.getMessage());
        }
    }

    public Mercadoria atualizarMercadoria(Long id, Mercadoria mercadoria) {
        try {
            Mercadoria mercadoriaAtualizada = repositorioMercadoria.findById(id)
                    .orElseThrow(() -> new RuntimeException("Mercadoria não encontrada para atualização"));
            mapMercadoriaParaAtualizar(mercadoriaAtualizada, mercadoria);
            return repositorioMercadoria.save(mercadoriaAtualizada);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar mercadoria: " + e.getMessage());
        }
    }

    public void deletarMercadoria(Long id) {
        try {
            if (!repositorioMercadoria.existsById(id)) {
                throw new RuntimeException("Mercadoria não encontrada para deleção");
            }
            repositorioMercadoria.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar mercadoria: " + e.getMessage());
        }
    }

    public Mercadoria buscarMercadoria(Long id) {
        try {
            Mercadoria mercadoria = repositorioMercadoria.findById(id)
                    .orElseThrow(() -> new RuntimeException("Mercadoria não encontrada"));
            return mercadoria;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar mercadoria: " + e.getMessage());
        }
    }

    public List<Mercadoria> buscarMercadorias() {
        try {
            return repositorioMercadoria.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar mercadorias: " + e.getMessage());
        }
    }

    private void mapMercadoriaParaAtualizar(Mercadoria mercadoriaExistente, Mercadoria novaMercadoria) {
        mercadoriaExistente.setNome(novaMercadoria.getNome());
        mercadoriaExistente.setDescricao(novaMercadoria.getDescricao());
        mercadoriaExistente.setQuantidade(novaMercadoria.getQuantidade());
        mercadoriaExistente.setValor(novaMercadoria.getValor());
        mercadoriaExistente.setValidade(novaMercadoria.getValidade());
        mercadoriaExistente.setFabricao(novaMercadoria.getFabricao());
        mercadoriaExistente.setCadastro(novaMercadoria.getCadastro());
    }
}
