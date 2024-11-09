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

    private void addLinks(Servico servico) {
        servico.add(linkTo(methodOn(ServicoControllers.class).buscarServico(servico.getId())).withSelfRel());
    }
}
