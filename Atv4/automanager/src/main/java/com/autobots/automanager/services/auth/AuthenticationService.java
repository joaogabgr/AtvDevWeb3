package com.autobots.automanager.services.auth;

import com.autobots.automanager.dto.auth.AuthenticationDTO;
import com.autobots.automanager.dto.auth.RegisterDTO;
import com.autobots.automanager.dto.models.UserDTO;
import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.excepition.SystemContextException;
import com.autobots.automanager.infra.security.TokenService;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private RepositorioUsuario userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String login(AuthenticationDTO data) throws SystemContextException {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);

            return tokenService.generateToken((Usuario) auth.getPrincipal());
        } catch (Exception e) {
            throw new SystemContextException(e.getMessage());
        }
    }

    public UserDTO register(RegisterDTO data) throws SystemContextException {
        try {
            if (this.userRepository.findByEmail(data.getEmail()) != null) {
                throw new SystemContextException("Email already registered");
            }

            String encodedPassword = new BCryptPasswordEncoder().encode(data.getPassword());
            Usuario user = new Usuario(data.getName(), data.getEmail(), encodedPassword, data.getRole());
            this.userRepository.save(user);
            return new UserDTO(user.getUsername(), user.getEmail(), user.getRole());
        } catch (Exception e) {
            throw new SystemContextException(e.getMessage());
        }
    }
}

