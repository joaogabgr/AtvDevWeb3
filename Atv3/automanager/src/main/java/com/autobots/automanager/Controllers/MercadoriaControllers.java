package com.autobots.automanager.Controllers;

import com.autobots.automanager.dto.ResponseModelDTO;
import com.autobots.automanager.entitades.Mercadoria;
import com.autobots.automanager.services.MercadoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaControllers {

    @Autowired
    private MercadoriaService mercadoriaService;

    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarMercadoria(@RequestBody Mercadoria mercadoria) {
        try {
            Mercadoria mercadoriaCriada = mercadoriaService.criarMercadoria(mercadoria);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.CREATED, mercadoriaCriada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarMercadoria(@PathVariable Long id, @RequestBody Mercadoria mercadoria) {
        try {
            Mercadoria mercadoriaAtualizada = mercadoriaService.atualizarMercadoria(id, mercadoria);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, mercadoriaAtualizada));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarMercadoria(@PathVariable Long id) {
        try {
            mercadoriaService.deletarMercadoria(id);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, "Mercadoria deletada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarMercadoria(@PathVariable Long id) {
        try {
            Mercadoria mercadoria = mercadoriaService.buscarMercadoria(id);
            addLinks(mercadoria);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, mercadoria));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarMercadorias() {
        try {
            List<Mercadoria> mercadorias = mercadoriaService.buscarMercadorias();
            mercadorias.forEach(this::addLinks);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, mercadorias));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    private void addLinks(Mercadoria mercadoria) {
        mercadoria.add(linkTo(methodOn(MercadoriaControllers.class).buscarMercadoria(mercadoria.getId())).withSelfRel());
        mercadoria.add(linkTo(methodOn(MercadoriaControllers.class).buscarMercadorias()).withRel("mercadorias"));
    }
}
