package com.auth.jwt.service;

import com.auth.jwt.model.Usuario;
import com.auth.jwt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {

    @Autowired
    private UserRepository UserRepository;

    public Usuario atualizarUsuario(Long id, Usuario usuario, String role) {
        try {
            Usuario usuarioAtualizado = UserRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado para atualização"));

            if (!verificadorRole(String.valueOf(usuarioAtualizado.getRole()), role)) {
                throw new RuntimeException("Usuário não tem permissão para atualizar");
            }
            mapUsuarioParaAtualizar(usuarioAtualizado, usuario);
            return UserRepository.save(usuarioAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void deletarUsuario(Long id, String role) {
        try {
            Usuario usuario = UserRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado para deleção"));

            if (!verificadorRole(String.valueOf(usuario.getRole()), role)) {
                throw new RuntimeException("Usuário não tem permissão para deletar");
            }
            UserRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    public Usuario buscarUsuario(String email, String role) {
        try {
            Usuario usuario = (Usuario) UserRepository.findByEmail(email);

            if (!verificadorRole(String.valueOf(usuario.getRole()), role)) {
                throw new RuntimeException("Usuário não tem permissão para visualizar");
            }
            return usuario;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    public Usuario buscarUsuario(Authentication authentication) {
        try {
            return (Usuario) UserRepository.findByEmail(authentication.getName());
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> buscarUsuarios() {
        try {
            return UserRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage());
        }
    }

    private void mapUsuarioParaAtualizar(Usuario usuarioAtualizado, Usuario usuario) {
        usuarioAtualizado.setName(usuario.getName() != null ? usuario.getName() : usuarioAtualizado.getName());
        usuarioAtualizado.setEmail(usuario.getEmail() != null ? usuario.getEmail() : usuarioAtualizado.getEmail());
        usuarioAtualizado.setPassword(usuario.getPassword() != null ? usuario.getPassword() : usuarioAtualizado.getPassword());
        usuarioAtualizado.setRole(usuario.getRole() != null ? usuario.getRole() : usuarioAtualizado.getRole());

        if (usuario.getTelefones() != null) {
            usuarioAtualizado.getTelefones().clear();
            usuarioAtualizado.getTelefones().addAll(usuario.getTelefones());
        }

        if (usuario.getDocumentos() != null) {
            usuarioAtualizado.getDocumentos().clear();
            usuarioAtualizado.getDocumentos().addAll(usuario.getDocumentos());
        }

        if (usuario.getEmails() != null) {
            usuarioAtualizado.getEmails().clear();
            usuarioAtualizado.getEmails().addAll(usuario.getEmails());
        }

        if (usuario.getCredenciais() != null) {
            usuarioAtualizado.getCredenciais().clear();
            usuarioAtualizado.getCredenciais().addAll(usuario.getCredenciais());
        }

        if (usuario.getMercadorias() != null) {
            usuarioAtualizado.getMercadorias().clear();
            usuarioAtualizado.getMercadorias().addAll(usuario.getMercadorias());
        }

        if (usuario.getVendas() != null) {
            usuarioAtualizado.getVendas().clear();
            usuarioAtualizado.getVendas().addAll(usuario.getVendas());
        }

        if (usuario.getVeiculos() != null) {
            usuarioAtualizado.getVeiculos().clear();
            usuarioAtualizado.getVeiculos().addAll(usuario.getVeiculos());
        }

        if (usuario.getEndereco() != null) {
            usuarioAtualizado.setEndereco(usuario.getEndereco());
        }

        if (usuario.getPerfis() != null) {
            usuarioAtualizado.getPerfis().clear();
            usuarioAtualizado.getPerfis().addAll(usuario.getPerfis());
        }
    }


    private boolean verificadorRole(String usuarioRole, String logadoRole) {
        if (Objects.equals(logadoRole, "ADMIN")) {
            return true;
        }
        if (Objects.equals(logadoRole, "GERENTE") && !Objects.equals(usuarioRole, "ADMIN")) {
            return true;
        }
        if (Objects.equals(logadoRole, "VENDEDOR") && (Objects.equals(usuarioRole, "CLIENTE"))) {
            return true;
        }
        return Objects.equals(logadoRole, "CLIENTE") && Objects.equals(usuarioRole, "CLIENTE");
    }
}
