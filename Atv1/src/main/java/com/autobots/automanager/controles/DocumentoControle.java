package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.services.DocumentosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/documento")
public class DocumentoControle {

    @Autowired
    private DocumentosServices documentosServices;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastroDocumento(@RequestBody Documento data) {
        try {
            documentosServices.cadastrarDocumento(data);
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/atualizacao")
    public ResponseEntity<?> atualizacaoDocumento(@RequestBody Documento data) {
        try {
            Documento documento = documentosServices.atualizarDocumento(data);
            return ResponseEntity.ok().body(documento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarDocumento(@PathVariable Long id) {
        try {
            documentosServices.deletarDocumento(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarDocumento(@PathVariable Long id) {
        try {
            Documento documento = documentosServices.buscarDocumento(id);
            return ResponseEntity.ok().body(documento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarDocumentos() {
        try {
            return ResponseEntity.ok().body(documentosServices.buscarDocumentos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
