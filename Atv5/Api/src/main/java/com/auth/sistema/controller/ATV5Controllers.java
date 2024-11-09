package com.auth.sistema.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/atv5")
public class ATV5Controllers {

    @Autowired
    private RestTemplate restTemplate; // RestTemplate para chamadas ao microserviço

    private static final String MICROSERVICE_URL = "http://localhost:8081/atv5/";

    // Método para pegar o token da requisição
    private String getTokenFromRequest(HttpServletRequest request) {
        // Pegando o token do cabeçalho Authorization
        String authorizationHeader = request.getHeader("Authorization");

        // Validando se o token existe e começa com 'Bearer '
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            return authorizationHeader.substring(7); // Retira o 'Bearer ' e retorna o token
        }
        return null; // Retorna null se o token não estiver presente
    }

    // Método genérico para chamada ao microserviço com token
    private ResponseEntity<String> callMicroserviceWithToken(String url, String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            if (token != null) {
                headers.set("Authorization", "Bearer " + token); // Adiciona o token no cabeçalho
            }
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Faz a requisição para o microserviço, passando o cabeçalho com o token
            return restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/teste")
    public ResponseEntity<?> teste() {
        return ResponseEntity.ok().body("Teste de rota");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listClienteForEmpresa/{empresaID}")
    public ResponseEntity<?> listClienteForEmpresa(@PathVariable String empresaID, HttpServletRequest request) {
        String url = MICROSERVICE_URL + "listClienteForEmpresa/" + empresaID;
        String token = getTokenFromRequest(request); // Obtém o token da requisição
        return callMicroserviceWithToken(url, token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listFuncionarioForEmpresa/{empresaID}")
    public ResponseEntity<?> listFuncionarioForEmpresa(@PathVariable String empresaID, HttpServletRequest request) {
        String url = MICROSERVICE_URL + "listFuncionarioForEmpresa/" + empresaID;
        String token = getTokenFromRequest(request); // Obtém o token da requisição
        return callMicroserviceWithToken(url, token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listMercadoriaForEmpresa/{empresaID}")
    public ResponseEntity<?> listMercadoriaForEmpresa(@PathVariable String empresaID, HttpServletRequest request) {
        String url = MICROSERVICE_URL + "listMercadoriaForEmpresa/" + empresaID;
        String token = getTokenFromRequest(request); // Obtém o token da requisição
        return callMicroserviceWithToken(url, token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listServicoForEmpresa/{empresaID}")
    public ResponseEntity<?> listServicoForEmpresa(@PathVariable String empresaID, HttpServletRequest request) {
        String url = MICROSERVICE_URL + "listServicoForEmpresa/" + empresaID;
        String token = getTokenFromRequest(request); // Obtém o token da requisição
        return callMicroserviceWithToken(url, token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listVendaPorEmpresaEData/{empresaID}/{startDate}/{endDate}")
    public ResponseEntity<?> listVendaPorEmpresaEData(@PathVariable String empresaID, @PathVariable String startDate, @PathVariable String endDate, HttpServletRequest request) {
        String url = MICROSERVICE_URL + "listVendaPorEmpresaEData/" + empresaID + "/" + startDate + "/" + endDate;
        String token = getTokenFromRequest(request); // Obtém o token da requisição
        return callMicroserviceWithToken(url, token);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/listVeiculoForEmpresa/{empresaID}")
    public ResponseEntity<?> listVeiculoForEmpresa(@PathVariable String empresaID, HttpServletRequest request) {
        String url = MICROSERVICE_URL + "listVeiculoForEmpresa/" + empresaID;
        String token = getTokenFromRequest(request); // Obtém o token da requisição
        return callMicroserviceWithToken(url, token);
    }
}
