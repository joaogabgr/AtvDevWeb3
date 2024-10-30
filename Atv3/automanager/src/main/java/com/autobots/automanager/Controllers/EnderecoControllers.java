package com.autobots.automanager.Controllers;

import com.autobots.automanager.dto.ResponseModelDTO;
import com.autobots.automanager.entitades.Endereco;
import com.autobots.automanager.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/endereco")
public class EnderecoControllers {

    @Autowired
    private EnderecoService enderecoService;

    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarEndereco(@RequestBody Endereco endereco) {
        try {
            Endereco enderecoCriado = enderecoService.criarEndereco(endereco);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModelDTO(HttpStatus.CREATED, enderecoCriado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar endereço: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarEndereco(@PathVariable Long id) {
        try {
            Endereco endereco = enderecoService.buscarEndereco(id);
            addLinks(endereco);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, endereco));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar endereço: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarEnderecos() {
        try {
            List<Endereco> enderecos = enderecoService.buscarEnderecos();
            enderecos.forEach(this::addLinks);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, enderecos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar endereços: " + e.getMessage()));
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarEndereco(@PathVariable Long id, @RequestBody Endereco endereco) {
        try {
            Endereco enderecoAtualizado = enderecoService.atualizarEndereco(id, endereco);
            addLinks(enderecoAtualizado);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, enderecoAtualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar endereço: " + e.getMessage()));
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarEndereco(@PathVariable Long id) {
        try {
            enderecoService.deletarEndereco(id);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, "Endereço deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar endereço: " + e.getMessage()));
        }
    }

    private void addLinks(Endereco endereco) {
        endereco.add(linkTo(methodOn(EnderecoControllers.class).buscarEndereco(endereco.getId())).withSelfRel());
        endereco.add(linkTo(methodOn(EnderecoControllers.class).buscarEnderecos()).withRel("enderecos"));
    }
}
