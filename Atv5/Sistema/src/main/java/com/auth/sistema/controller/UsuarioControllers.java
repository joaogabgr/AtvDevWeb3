package com.auth.sistema.controller;

import com.auth.sistema.dto.auth.RegisterDTO;
import com.auth.sistema.dto.web.ResponseModelDTO;
import com.auth.sistema.enums.UserRole;
import com.auth.sistema.repositories.UserRepository;
import com.auth.sistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UsuarioControllers {

    @Autowired
    private UsuarioService usuarioService;

    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarUsuario(@PathVariable Long id) {
        try {
            var usuario = usuarioService.buscarUsuario(id);
            var response = new ResponseModelDTO(usuario);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO("Erro ao buscar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
