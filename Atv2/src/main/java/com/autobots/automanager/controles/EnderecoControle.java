package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.repositorios.EnderecoRepositorio;
import com.autobots.automanager.services.EnderecosServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/endereco")
public class EnderecoControle {

    @Autowired
    private EnderecosServices enderecosServices;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrarEndereco(@RequestBody Endereco data) {
        try {
            enderecosServices.cadastrarEndereco(data);
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/atualizacao")
    public ResponseEntity<?> atualizarEndereco(@RequestBody Endereco data) {
        try {
            Endereco endereco = enderecosServices.atualizarEndereco(data);
            return ResponseEntity.ok().body(endereco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarEndereco(@PathVariable Long id) {
        try {
            enderecosServices.deletarEndereco(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarEndereco(@PathVariable Long id) {
        try {
            Endereco endereco = enderecosServices.buscarEndereco(id);
            return ResponseEntity.ok().body(endereco);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarEnderecos() {
        try {
            return ResponseEntity.ok().body(enderecosServices.buscarEnderecos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
