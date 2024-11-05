package com.autobots.automanager.Controllers;

import com.autobots.automanager.dto.web.ResponseModelDTO;
import com.autobots.automanager.entitades.Telefone;
import com.autobots.automanager.services.TelefoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/telefones")
public class TelefoneControllers {

    @Autowired
    private TelefoneService telefoneService;

    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarTelefone(@RequestBody Telefone telefone) {
        try {
            Telefone telefoneCriado = telefoneService.criarTelefone(telefone);
            var response = new ResponseModelDTO(HttpStatus.CREATED, telefoneCriado);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar telefone: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarTelefone(@PathVariable Long id, @RequestBody Telefone telefone) {
        try {
            Telefone telefoneAtualizado = telefoneService.atualizarTelefone(id, telefone);
            var response = new ResponseModelDTO(HttpStatus.OK, telefoneAtualizado);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar telefone: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarTelefone(@PathVariable Long id) {
        try {
            telefoneService.deletarTelefone(id);
            var response = new ResponseModelDTO(HttpStatus.OK, "Telefone deletado com sucesso");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar telefone: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarTelefone(@PathVariable Long id) {
        try {
            Telefone telefone = telefoneService.buscarTelefone(id);
            addLinks(telefone);
            var response = new ResponseModelDTO(HttpStatus.OK, telefone);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar telefone: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarTelefones() {
        try {
            var telefones = telefoneService.buscarTelefones();
            telefones.forEach(this::addLinks);
            var response = new ResponseModelDTO(HttpStatus.OK, telefones);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar telefones: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private void addLinks(Telefone telefone) {
        telefone.add(linkTo(methodOn(TelefoneControllers.class).buscarTelefone(telefone.getId())).withSelfRel());
        telefone.add(linkTo(methodOn(TelefoneControllers.class).buscarTelefones()).withRel("telefones"));
    }
}
