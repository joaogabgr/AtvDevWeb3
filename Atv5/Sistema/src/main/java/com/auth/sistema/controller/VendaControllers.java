package com.auth.sistema.controller;

import com.auth.sistema.dto.web.ResponseModelDTO;
import com.auth.sistema.model.Usuario;
import com.auth.sistema.model.Venda;
import com.auth.sistema.service.UsuarioService;
import com.auth.sistema.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/vendas")
public class VendaControllers {

    @Autowired
    private VendaService vendaService;


    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarVenda(@PathVariable Long id) {
        try {
            Venda venda = vendaService.buscarVenda(id);
            addLinks(venda);
            var response = new ResponseModelDTO(HttpStatus.OK, venda);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar venda: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private void addLinks(Venda venda) {
        venda.add(linkTo(methodOn(VendaControllers.class).buscarVenda(venda.getId())).withSelfRel());
    }
}
