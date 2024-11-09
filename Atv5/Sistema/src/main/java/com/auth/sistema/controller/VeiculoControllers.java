package com.auth.sistema.controller;

import com.auth.sistema.dto.web.ResponseModelDTO;
import com.auth.sistema.model.Veiculo;
import com.auth.sistema.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/veiculos")
public class VeiculoControllers {

    @Autowired
    private VeiculoService veiculoService;

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

    private void addLinks(Veiculo veiculo) {
        veiculo.add(linkTo(methodOn(VeiculoControllers.class).buscarVeiculo(veiculo.getId())).withSelfRel());
    }

}
