package com.autobots.automanager.Controllers;

import com.autobots.automanager.dto.ResponseModelDTO;
import com.autobots.automanager.entitades.Servico;
import com.autobots.automanager.services.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/servico")
public class ServicoControllers {

    @Autowired
    private ServicoService servicoService;

    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarServico(@RequestBody Servico servico) {
        try {
            Servico servicoCriado = servicoService.criarServico(servico);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModelDTO(HttpStatus.CREATED, servicoCriado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar servico: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarServico(@PathVariable Long id) {
        try {
            Servico servico = servicoService.buscarServico(id);
            addLinks(servico);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, servico));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar servico: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarServicos() {
        try {
            List<Servico> servico = servicoService.buscarServicos();
            servico.forEach(this::addLinks);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, servico));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar servicos: " + e.getMessage()));
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        try {
            Servico servicoAtualizado = servicoService.atualizarServico(id, servico);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, servicoAtualizado));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar servico: " + e.getMessage()));
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarServico(@PathVariable Long id) {
        try {
            servicoService.deletarServico(id);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, "Servico deletado com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar servico: " + e.getMessage()));
        }
    }

    private void addLinks(Servico servico) {
        servico.add(linkTo(methodOn(ServicoControllers.class).buscarServico(servico.getId())).withSelfRel());
        servico.add(linkTo(methodOn(ServicoControllers.class).buscarServicos()).withRel("servicos"));
    }
}
