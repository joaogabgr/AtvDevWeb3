package com.auth.jwt.controller;

import com.auth.jwt.dto.web.ResponseModelDTO;
import com.auth.jwt.model.Usuario;
import com.auth.jwt.model.Venda;
import com.auth.jwt.service.UsuarioService;
import com.auth.jwt.service.VendaService;
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

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarVenda(@RequestBody Venda venda) {
        try {
            Venda vendaCriada = vendaService.criarVenda(venda);
            var response = new ResponseModelDTO(HttpStatus.CREATED, vendaCriada);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar venda: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @PostMapping("/criar-vendaPorVendedor")
    public ResponseEntity<ResponseModelDTO> criarVendaPorVendedor(@RequestBody Venda venda, Authentication authentication) {
        try {
            Usuario funcionario = usuarioService.buscarUsuario(authentication.getName(), "VENDEDOR");
            venda.setFuncionario(funcionario);
            System.out.println(funcionario);
            Venda vendaCriada = vendaService.criarVenda(venda);
            var response = new ResponseModelDTO(HttpStatus.CREATED, vendaCriada);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar venda: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarVenda(@PathVariable Long id, @RequestBody Venda venda) {
        try {
            Venda vendaAtualizada = vendaService.atualizarVenda(id, venda);
            var response = new ResponseModelDTO(HttpStatus.OK, vendaAtualizada);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar venda: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarVenda(@PathVariable Long id) {
        try {
            vendaService.deletarVenda(id);
            var response = new ResponseModelDTO(HttpStatus.OK, "Venda deletada com sucesso");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar venda: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

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

    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarVendas() {
        try {
            var vendas = vendaService.buscarVendas();
            vendas.forEach(this::addLinks);
            var response = new ResponseModelDTO(HttpStatus.OK, vendas);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar vendas: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private void addLinks(Venda venda) {
        venda.add(linkTo(methodOn(VendaControllers.class).buscarVenda(venda.getId())).withSelfRel());
        venda.add(linkTo(methodOn(VendaControllers.class).buscarVendas()).withRel("Vendas"));
    }
}
