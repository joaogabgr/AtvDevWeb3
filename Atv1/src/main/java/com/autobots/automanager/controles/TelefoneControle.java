package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.services.TelefoneServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/telefone")
public class TelefoneControle {

    @Autowired
    private TelefoneServices telefoneServices;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastroTelefone(@RequestBody Telefone data) {
        try {
            telefoneServices.cadastrarTelefone(data);
            return ResponseEntity.ok().body(data);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/atualizacao")
    public ResponseEntity<?> atualizacaoTelefone(@RequestBody Telefone data) {
        try {
            Telefone telefone = telefoneServices.atualizarTelefone(data);
            return ResponseEntity.ok().body(telefone);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarTelefone(@PathVariable Long id) {
        try {
            telefoneServices.deletarTelefone(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarTelefone(@PathVariable Long id) {
        try {
            Telefone telefone = telefoneServices.buscarTelefone(id);
            return ResponseEntity.ok().body(telefone);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<?> listarTelefones() {
        try {
            return ResponseEntity.ok().body(telefoneServices.buscarTelefones());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
