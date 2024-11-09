package com.auth.sistema.controller;

import com.auth.sistema.dto.web.ResponseModelDTO;
import com.auth.sistema.model.Mercadoria;
import com.auth.sistema.model.Servico;
import com.auth.sistema.model.Veiculo;
import com.auth.sistema.model.Venda;
import com.auth.sistema.service.Atv5Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/atv5")
public class ATV5Controllers {

    @Autowired
    private Atv5Services atv5Services;

    @GetMapping("/listClienteForEmpresa/{empresaID}")
    public ResponseEntity<ResponseModelDTO> listClienteForEmpresa(@PathVariable String empresaID) {
        try {
            return ResponseEntity.ok().body(new ResponseModelDTO(atv5Services.listClienteForEmpresa(Long.valueOf(empresaID))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/listFuncionarioForEmpresa/{empresaID}")
    public ResponseEntity<ResponseModelDTO> listFuncionarioForEmpresa(@PathVariable String empresaID) {
        try {
            return ResponseEntity.ok().body(new ResponseModelDTO(atv5Services.listFuncionarioForEmpresa(Long.valueOf(empresaID))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/listMercadoriaForEmpresa/{empresaID}")
    public ResponseEntity<ResponseModelDTO> listMercadoriaForEmpresa(@PathVariable String empresaID) {
        try {
            List<Mercadoria> mercadorias = atv5Services.listMercadoriaForEmpresa(Long.valueOf(empresaID));
            mercadorias.forEach(this::addLinkMercadoria);
            return ResponseEntity.ok().body(new ResponseModelDTO(mercadorias));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/listServicoForEmpresa/{empresaID}")
    public ResponseEntity<ResponseModelDTO> listServicoForEmpresa(@PathVariable String empresaID) {
        try {
            List<Servico> servicos = atv5Services.listServicoForEmpresa(Long.valueOf(empresaID));
            servicos.forEach(this::addLinkServico);
            return ResponseEntity.ok().body(new ResponseModelDTO(servicos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/listVendaPorEmpresaEData/{empresaID}/{startDate}/{endDate}")
    public ResponseEntity<ResponseModelDTO> listVendaPorEmpresaEData(
            @PathVariable String empresaID,
            @PathVariable Date startDate,
            @PathVariable Date endDate) {
        try {
            List<Venda> vendas = atv5Services.listVendaPorEmpresaEData(Long.valueOf(empresaID), startDate, endDate);
            vendas.forEach(this::addLinkVenda);
            return ResponseEntity.ok().body(new ResponseModelDTO(vendas));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/listVeiculoForEmpresa/{empresaID}")
    public ResponseEntity<ResponseModelDTO> listVeiculoForEmpresa(@PathVariable String empresaID) {
        try {
            List<Veiculo> veiculos = atv5Services.listVeiculoForEmpresa(Long.valueOf(empresaID));
            veiculos.forEach(this::addLinkVeiculo);
            return ResponseEntity.ok().body(new ResponseModelDTO(veiculos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(HttpStatus.BAD_REQUEST, e.getMessage()));
        }
    }

    private void addLinkVeiculo(Veiculo veiculo) {
        veiculo.add(linkTo(methodOn(VeiculoControllers.class).buscarVeiculo(veiculo.getId())).withSelfRel().withHref("http://localhost:8080/veiculos/buscar/" + veiculo.getId()));
    }

    private void addLinkVenda(Venda venda) {
        venda.add(linkTo(methodOn(VendaControllers.class).buscarVenda(venda.getId())).withSelfRel().withHref("http://localhost:8080/vendas/buscar/" + venda.getId()));
    }

    private void addLinkServico(Servico servico) {
        servico.add(linkTo(methodOn(ServicoControllers.class).buscarServico(servico.getId())).withSelfRel().withHref("http://localhost:8080/servicos/buscar/" + servico.getId()));
    }

    private void addLinkMercadoria(Mercadoria mercadoria) {
        mercadoria.add(linkTo(methodOn(MercadoriaControllers.class).buscarMercadoria(mercadoria.getId())).withSelfRel().withHref("http://localhost:8080/mercadorias/buscar/" + mercadoria.getId()));
    }
}
