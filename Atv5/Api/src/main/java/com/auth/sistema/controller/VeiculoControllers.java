package com.auth.sistema.controller;

import com.auth.sistema.dto.web.ResponseModelDTO;
import com.auth.sistema.model.Veiculo;
import com.auth.sistema.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/veiculos")
public class VeiculoControllers {

    @Autowired
    private VeiculoService veiculoService;

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarVeiculo(@RequestBody Veiculo veiculo) {
        try {
            Veiculo veiculoCriado = veiculoService.criarVeiculo(veiculo);
            var response = new ResponseModelDTO(HttpStatus.CREATED, veiculoCriado);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar veículo: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarVeiculo(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        try {
            Veiculo veiculoAtualizado = veiculoService.atualizarVeiculo(id, veiculo);
            var response = new ResponseModelDTO(HttpStatus.OK, veiculoAtualizado);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar veículo: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarVeiculo(@PathVariable Long id) {
        try {
            veiculoService.deletarVeiculo(id);
            var response = new ResponseModelDTO(HttpStatus.OK, "Veículo deletado com sucesso");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar veículo: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarVeiculo(@PathVariable Long id) {
        try {
            Veiculo veiculo = veiculoService.buscarVeiculo(id);
            addLinks(veiculo);
            var response = new ResponseModelDTO(HttpStatus.OK, veiculo);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar veículo: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarVeiculos() {
        try {
            List<Veiculo> veiculos = veiculoService.buscarVeiculos();
            veiculos.forEach(this::addLinks);
            var response = new ResponseModelDTO(HttpStatus.OK, veiculos);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar veículos: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private void addLinks(Veiculo veiculo) {
        veiculo.add(linkTo(methodOn(VeiculoControllers.class).buscarVeiculo(veiculo.getId())).withSelfRel());
        veiculo.add(linkTo(methodOn(VeiculoControllers.class).buscarVeiculos()).withRel("Veiculos"));
    }

}
