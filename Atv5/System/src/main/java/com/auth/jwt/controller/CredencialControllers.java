package com.auth.jwt.controller;

import com.auth.jwt.dto.web.ResponseModelDTO;
import com.auth.jwt.model.Credencial;
import com.auth.jwt.service.CredencialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/credenciais")
public class CredencialControllers {

    @Autowired
    private CredencialService credencialService;

    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarCredencial(@RequestBody Credencial credencial) {
        try {
            Credencial credencialCriado = credencialService.criarCredencial(credencial);
            var response = new ResponseModelDTO(HttpStatus.CREATED, credencialCriado);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar credencial: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarCredencial(@PathVariable Long id, @RequestBody Credencial credencial) {
        try {
            Credencial credencialAtualizado = credencialService.atualizarCredencial(id, credencial);
            var response = new ResponseModelDTO(HttpStatus.OK, credencialAtualizado);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar credencial: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarCredencial(@PathVariable Long id) {
        try {
            credencialService.deletarCredencial(id);
            var response = new ResponseModelDTO(HttpStatus.OK, "Credencial deletada com sucesso");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar credencial: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarCredencial(@PathVariable Long id) {
        try {
            Credencial credencial = credencialService.buscarCredencial(id);
            addLinks(credencial);
            var response = new ResponseModelDTO(HttpStatus.OK, credencial);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar credencial: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarCredenciais() {
        try {
            var credenciais = credencialService.buscarCredenciais();
            credenciais.forEach(this::addLinks);
            var response = new ResponseModelDTO(HttpStatus.OK, credenciais);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar credenciais: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private void addLinks(Credencial credencial) {
        credencial.add(linkTo(methodOn(CredencialControllers.class).buscarCredencial(credencial.getId())).withSelfRel());
        credencial.add(linkTo(methodOn(CredencialControllers.class).buscarCredenciais()).withRel("credenciais"));
    }
}
