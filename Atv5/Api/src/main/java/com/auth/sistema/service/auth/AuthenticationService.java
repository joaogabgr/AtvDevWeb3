package com.auth.sistema.service.auth;

import com.auth.sistema.dto.UserDTO;
import com.auth.sistema.dto.auth.AuthenticationDTO;
import com.auth.sistema.dto.auth.RegisterDTO;
import com.auth.sistema.excepition.SystemContextException;
import com.auth.sistema.infra.security.TokenService;
import com.auth.sistema.model.Usuario;
import com.auth.sistema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

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
            Usuario usuario = new Usuario(data.getName(), data.getEmail(), encodedPassword, data.getRole());
            this.userRepository.save(usuario);
            return new UserDTO(usuario.getName(), usuario.getEmail(), usuario.getRole());
        } catch (Exception e) {
            throw new SystemContextException(e.getMessage());
        }
    }

    public Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Usuario) authentication.getPrincipal();
    }

}
