package com.autobots.automanager.Controllers;

import com.autobots.automanager.dto.ResponseModelDTO;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControllers {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
            var response = new ResponseModelDTO(HttpStatus.CREATED, usuarioCriado);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioAtualizado = usuarioService.atulizarUsuario(id, usuario);
            var response = new ResponseModelDTO(HttpStatus.OK, usuarioAtualizado);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deletarUsuario(id);
            var response = new ResponseModelDTO(HttpStatus.OK, "Usuário deletado com sucesso");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.buscarUsuario(id);
            addLinks(usuario);
            var response = new ResponseModelDTO(HttpStatus.OK, usuario);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar usuário: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.buscarUsuarios();
            usuarios.forEach(this::addLinks);
            var response = new ResponseModelDTO(HttpStatus.OK, usuarios);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            var response = new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar usuários: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private void addLinks(Usuario usuario) {
        usuario.add(linkTo(methodOn(UsuarioControllers.class).buscarUsuario(usuario.getId())).withSelfRel());
        usuario.add(linkTo(methodOn(UsuarioControllers.class).buscarUsuarios()).withRel("usuarios"));
    }
}
