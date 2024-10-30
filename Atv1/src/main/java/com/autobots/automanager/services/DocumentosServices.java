package com.autobots.automanager.services;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.DocumentosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentosServices {

    @Autowired
    private DocumentosRepositorio repositorio;

    public void cadastrarDocumento(Documento data) {
        try {
            repositorio.save(data);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao cadastrar documento");
        }
    }

    public Documento atualizarDocumento(Documento data) {
        try {
            Documento documento = repositorio.findById(data.getId()).orElseThrow(
                    () -> new RuntimeException("Documento não encontrado")
            );
            documento.setTipo(data.getTipo());
            documento.setNumero(data.getNumero());
            repositorio.save(documento);
            return documento;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar documento");
        }
    }

    public void deletarDocumento(Long id) {
        try {
            Documento documento = repositorio.findById(id).orElseThrow(
                    () -> new RuntimeException("Documento não encontrado")
            );
            repositorio.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar documento");
        }
    }

    public Documento buscarDocumento(Long id) {
        try {
            Documento documento = repositorio.findById(id).orElseThrow(
                    () -> new RuntimeException("Documento não encontrado")
            );
            return documento;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar documento");
        }
    }

    public List<Documento> buscarDocumentos() {
        try {
            return repositorio.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao listar documentos");
        }
    }
}
