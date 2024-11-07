package com.auth.jwt.controller;

import com.auth.jwt.dto.web.ResponseModelDTO;
import com.auth.jwt.service.Atv5Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/atv5")
public class ATV5Controllers {

    @Autowired
    private Atv5Services atv5Services;

    @GetMapping("/listClienteForEmpresa")
    public ResponseEntity<ResponseModelDTO> listClienteForEmpresa(String empresaID) {
        try {
            return ResponseEntity.ok().body(new ResponseModelDTO(atv5Services.listClienteForEmpresa(Long.valueOf(empresaID))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(e.getMessage()));
        }
    }

    @GetMapping("/listFuncionarioForEmpresa")
    public ResponseEntity<ResponseModelDTO> listFuncionarioForEmpresa(String empresaID) {
        try {
            return ResponseEntity.ok().body(new ResponseModelDTO(atv5Services.listFuncionarioForEmpresa(Long.valueOf(empresaID))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(e.getMessage()));
        }
    }

    @GetMapping("/listMercadoriaForEmpresa")
    public ResponseEntity<ResponseModelDTO> listMercadoriaForEmpresa(String empresaID) {
        try {
            return ResponseEntity.ok().body(new ResponseModelDTO(atv5Services.listMercadoriaForEmpresa(Long.valueOf(empresaID))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(e.getMessage()));
        }
    }

    @GetMapping("/listServicoForEmpresa")
    public ResponseEntity<ResponseModelDTO> listServicoForEmpresa(String empresaID) {
        try {
            return ResponseEntity.ok().body(new ResponseModelDTO(atv5Services.listServicoForEmpresa(Long.valueOf(empresaID))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(e.getMessage()));
        }
    }

    @GetMapping("listVendaPorEmpresaEData")
    public ResponseEntity<ResponseModelDTO> listVendaPorEmpresaEData(String empresaID, Date startDate, Date endDate) {
        try {
            return ResponseEntity.ok().body(new ResponseModelDTO(atv5Services.listVendaPorEmpresaEData(Long.valueOf(empresaID), startDate, endDate)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(e.getMessage()));
        }
    }

    @GetMapping("listVeiculoForEmpresa")
    public ResponseEntity<ResponseModelDTO> listVeiculoForEmpresa(String empresaID) {
        try {
            return ResponseEntity.ok().body(new ResponseModelDTO(new ResponseModelDTO(atv5Services.listVeiculoForEmpresa(Long.valueOf(empresaID)))));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseModelDTO(e.getMessage()));
        }
    }
}
