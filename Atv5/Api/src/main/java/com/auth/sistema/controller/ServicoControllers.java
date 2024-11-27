package com.auth.sistema.controller;

import com.auth.sistema.dto.web.ResponseModelDTO;
import com.auth.sistema.model.Servico;
import com.auth.sistema.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/servico")
public class ServicoControllers {

    @Autowired
    private ServicoService servicoService;

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarServico(@RequestBody Servico servico) {
        try {
            Servico servicoCriado = servicoService.criarServico(servico);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModelDTO(HttpStatus.CREATED, servicoCriado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar servico: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarServico(@PathVariable Long id) {
        try {
            Servico servico = servicoService.buscarServico(id);
            addLinks(servico);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, servico));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar servico: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarServicos() {
        try {
            List<Servico> servico = servicoService.buscarServicos();
            servico.forEach(this::addLinks);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, servico));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar servicos: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        try {
            Servico servicoAtualizado = servicoService.atualizarServico(id, servico);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, servicoAtualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar servico: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarServico(@PathVariable Long id) {
        try {
            servicoService.deletarServico(id);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, "Servico deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar servico: " + e.getMessage()));
        }
    }

    private void addLinks(Servico servico) {
        servico.add(linkTo(methodOn(ServicoControllers.class).buscarServico(servico.getId())).withSelfRel());
        servico.add(linkTo(methodOn(ServicoControllers.class).buscarServicos()).withRel("servicos"));
    }
}
