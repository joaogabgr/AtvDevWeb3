package com.autobots.automanager.Controllers;

import com.autobots.automanager.dto.ResponseModelDTO;
import com.autobots.automanager.entitades.Documento;
import com.autobots.automanager.services.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/documentos")
public class DocumentosControllers {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarDocumento(@RequestBody Documento documento) {
        try {
            Documento documentoCriado = documentoService.criarDocumento(documento);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModelDTO(HttpStatus.CREATED, documentoCriado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar documento: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarDocumento(@PathVariable Long id) {
        try {
            Optional<Documento> documento = documentoService.buscarDocumento(id);
            return documento.map(doc -> {
                addLinks(doc);
                return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, doc));
            }).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseModelDTO(HttpStatus.NOT_FOUND, "Documento não encontrado")));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar documento: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarDocumentos() {
        try {
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, documentoService.buscarDocumentos()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar documentos: " + e.getMessage()));
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarDocumento(@PathVariable Long id, @RequestBody Documento documento) {
        try {
            Documento documentoAtualizado = documentoService.atualizarDocumento(id, documento);
            addLinks(documentoAtualizado);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, documentoAtualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar documento: " + e.getMessage()));
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarDocumento(@PathVariable Long id) {
        try {
            documentoService.deletarDocumento(id);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, "Documento deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar documento: " + e.getMessage()));
        }
    }

    private void addLinks(Documento documento) {
        documento.add(linkTo(methodOn(DocumentosControllers.class).buscarDocumento(documento.getId())).withSelfRel());
        documento.add(linkTo(methodOn(DocumentosControllers.class).buscarDocumentos()).withRel("documentos"));
    }
}
