package com.auth.sistema.controller;

import com.auth.sistema.dto.web.ResponseModelDTO;
import com.auth.sistema.model.Email;
import com.auth.sistema.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/email")
public class EmailControllers {

    @Autowired
    private EmailService emailService;

    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarEmail(@RequestBody Email email) {
        try {
            Email emailCriado = emailService.criarEmail(email);
            var response = new ResponseModelDTO(HttpStatus.CREATED, emailCriado);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarEmail(@PathVariable Long id, @RequestBody Email email) {
        try {
            Email emailAtualizado = emailService.atualizarEmail(id, email);
            var response = new ResponseModelDTO(HttpStatus.OK, emailAtualizado);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarEmail(@PathVariable Long id) {
        try {
            emailService.deletarEmail(id);
            var response = new ResponseModelDTO(HttpStatus.OK, "Email deletado com sucesso");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarEmail(@PathVariable Long id) {
        try {
            Email email = emailService.buscarEmail(id);
            addLinks(email);
            var response = new ResponseModelDTO(HttpStatus.OK, email);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarEmails() {
        try {
            var emails = emailService.buscarEmails();
            emails.forEach(this::addLinks);
            var response = new ResponseModelDTO(HttpStatus.OK, emails);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    private void addLinks(Email email) {
        email.add(linkTo(methodOn(EmailControllers.class).buscarEmail(email.getId())).withSelfRel());
        email.add(linkTo(methodOn(EmailControllers.class).buscarEmails()).withRel("emails"));
    }
}
