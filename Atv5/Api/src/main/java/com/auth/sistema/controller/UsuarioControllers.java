package com.auth.sistema.controller;

import com.auth.sistema.dto.web.ResponseModelDTO;
import com.auth.sistema.enums.UserRole;
import com.auth.sistema.excepition.SystemContextException;
import com.auth.sistema.infra.security.TokenService;
import com.auth.sistema.dto.auth.AuthenticationDTO;
import com.auth.sistema.dto.auth.LoginResponseDTO;
import com.auth.sistema.dto.auth.RegisterDTO;
import com.auth.sistema.repositories.UserRepository;
import com.auth.sistema.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UsuarioControllers {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) throws SystemContextException {
        try {
            var token = this.authenticationService.login(data);
            var response = new ResponseModelDTO(new LoginResponseDTO(token));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new SystemContextException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) throws SystemContextException {
        try {
            var user = this.authenticationService.register(data);
            var response = new ResponseModelDTO(user);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new SystemContextException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/register/gerente")
    public ResponseEntity<?> registerGerente(@RequestBody @Valid RegisterDTO data) throws SystemContextException {
        try {
            data.setRole("GERENTE");
            var user = this.authenticationService.register(data);
            var response = new ResponseModelDTO(user);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new SystemContextException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping("/register/vendedor")
    public ResponseEntity<?> registerVendedor(@RequestBody @Valid RegisterDTO data) throws SystemContextException {
        try {
            data.setRole("VENDEDOR");
            var user = this.authenticationService.register(data);
            var response = new ResponseModelDTO(user);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new SystemContextException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @PostMapping("/register/cliente")
    public ResponseEntity<?> registerCliente(@RequestBody @Valid RegisterDTO data) throws SystemContextException {
        try {
            data.setRole("CLIENTE");
            var user = this.authenticationService.register(data);
            var response = new ResponseModelDTO(user);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            throw new SystemContextException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarUsuarios() {
        try {
            var usuarios = userRepository.findAll();
            var response = new ResponseModelDTO(usuarios);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO("Erro ao buscar usuários: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarUsuario(@PathVariable Long id) {
        try {
            var usuario = userRepository.findById(id).
                    orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            var response = new ResponseModelDTO(usuario);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO("Erro ao buscar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('CLIENTE')")
    @GetMapping("/buscarProfile")
    public ResponseEntity<ResponseModelDTO> buscarProfile() {
        try {
            var usuario = authenticationService.getUsuarioLogado();
            System.out.println("Usuário logado: " + usuario.getUsername());
            System.out.println("Papel do usuário: " + usuario.getAuthorities());
            var response = new ResponseModelDTO(usuario);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO("Erro ao buscar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    @PreAuthorize("hasRole('VENDEDOR')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarUsuario(@PathVariable Long id, @RequestBody RegisterDTO data) {
        try {
            var usuario = userRepository.findById(id).
                    orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            usuario.setName(data.getName());
            usuario.setEmail(data.getEmail());
            usuario.setPassword(data.getPassword());
            usuario.setRole(UserRole.valueOf(data.getRole()));
            var response = new ResponseModelDTO(userRepository.save(usuario));
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO("Erro ao atualizar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PreAuthorize("hasRole('VENDEDOR')")
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarUsuario(@PathVariable Long id) {
        try {
            userRepository.deleteById(id);
            var response = new ResponseModelDTO("Usuário deletado com sucesso");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO("Erro ao deletar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
