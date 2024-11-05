package com.autobots.automanager.Controllers;

import com.autobots.automanager.dto.auth.AuthenticationDTO;
import com.autobots.automanager.dto.auth.LoginResponseDTO;
import com.autobots.automanager.dto.auth.RegisterDTO;
import com.autobots.automanager.dto.web.ResponseModelDTO;
import com.autobots.automanager.excepition.SystemContextException;
import com.autobots.automanager.infra.security.TokenService;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import com.autobots.automanager.services.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationControllers {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RepositorioUsuario userRepository;

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

    @PreAuthorize("hasRole('VENDEDOR')")
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
}