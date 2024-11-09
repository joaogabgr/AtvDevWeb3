package com.auth.sistema.service;

import com.auth.sistema.model.Usuario;
import com.auth.sistema.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository UserRepository;

    public Usuario buscarUsuario(Long id) {
        try {
            return UserRepository.findById(id).orElseThrow(
                    () -> new RuntimeException("Usuário não encontrado")
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
    }
}
