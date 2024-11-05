package com.autobots.automanager.Controllers;

import com.autobots.automanager.dto.web.ResponseModelDTO;
import com.autobots.automanager.entitades.Empresa;
import com.autobots.automanager.services.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/empresa")
public class EmpresaControllers {

    @Autowired
    private EmpresaService empresaService;

    @PostMapping("/criar")
    public ResponseEntity<ResponseModelDTO> criarEmpresa(@RequestBody Empresa empresa) {
        try {
            Empresa empresaCriada = empresaService.criarEmpresa(empresa);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseModelDTO(HttpStatus.CREATED, empresaCriada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao criar empresa: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<ResponseModelDTO> buscarEmpresa(@PathVariable Long id) {
        try {
            Empresa empresa = empresaService.buscarEmpresa(id);
            addLinks(empresa);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, empresa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar empresa: " + e.getMessage()));
        }
    }

    @GetMapping("/buscar")
    public ResponseEntity<ResponseModelDTO> buscarEmpresas() {
        try {
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, empresaService.buscarEmpresas()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao buscar empresas: " + e.getMessage()));
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<ResponseModelDTO> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        try {
            Empresa empresaAtualizada = empresaService.atualizarEmpresa(id, empresa);
            addLinks(empresaAtualizada);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, empresaAtualizada));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao atualizar empresa: " + e.getMessage()));
        }
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<ResponseModelDTO> deletarEmpresa(@PathVariable Long id) {
        try {
            empresaService.deletarEmpresa(id);
            return ResponseEntity.ok(new ResponseModelDTO(HttpStatus.OK, "Empresa deletada com sucesso"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, "Erro ao deletar empresa: " + e.getMessage()));
        }
    }

    private void addLinks(Empresa empresa) {
        empresa.add(linkTo(methodOn(EmpresaControllers.class).buscarEmpresa(empresa.getId())).withSelfRel());
        empresa.add(linkTo(methodOn(EmpresaControllers.class).buscarEmpresas()).withRel("empresas"));
    }
}
