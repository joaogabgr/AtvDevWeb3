package com.autobots.automanager.services;

import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.repositorios.RepositorioDocumento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private RepositorioDocumento repositorioDocumento;

    public Documento criarDocumento(Documento documento) {
        try {
            return repositorioDocumento.save(documento);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar documento: " + e.getMessage());
        }
    }

    public Documento atualizarDocumento(Long id, Documento documento) {
        try {
            Documento documentoExistente = repositorioDocumento.findById(id)
                    .orElseThrow(() -> new RuntimeException("Documento não encontrado"));
            mapDocumentoParaAtualizar(documentoExistente, documento);
            return repositorioDocumento.save(documentoExistente);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar documento: " + e.getMessage());
        }
    }

    public void deletarDocumento(Long id) {
        try {
            repositorioDocumento.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar documento: " + e.getMessage());
        }
    }

    public Optional<Documento> buscarDocumento(Long id) {
        try {
            return repositorioDocumento.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar documento: " + e.getMessage());
        }
    }

    public List<Documento> buscarDocumentos() {
        try {
            return repositorioDocumento.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar documentos: " + e.getMessage());
        }
    }

    private void mapDocumentoParaAtualizar(Documento documentoExistente, Documento novoDocumento) {
        if (novoDocumento.getTipo() != null) {
            documentoExistente.setTipo(novoDocumento.getTipo());
        }
        if (novoDocumento.getDataEmissao() != null) {
            documentoExistente.setDataEmissao(novoDocumento.getDataEmissao());
        }
        if (novoDocumento.getNumero() != null) {
            documentoExistente.setNumero(novoDocumento.getNumero());
        }
    }
}
