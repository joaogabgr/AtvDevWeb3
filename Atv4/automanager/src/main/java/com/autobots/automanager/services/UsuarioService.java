package com.autobots.automanager.services;

import com.autobots.automanager.entitades.Usuario;
import com.autobots.automanager.repositorios.RepositorioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    public Usuario atulizarUsuario(Long id, Usuario usuario) {
        try {
            Usuario usuarioAtualizado = repositorioUsuario.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado para atualização"));

            mapUsuarioParaAtualizar(usuarioAtualizado, usuario);
            return repositorioUsuario.save(usuarioAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage());
        }
    }

    public void deletarUsuario(Long id) {
        try {
            if (!repositorioUsuario.existsById(id)) {
                throw new RuntimeException("Usuário não encontrado para deleção");
            }
            repositorioUsuario.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar usuário: " + e.getMessage());
        }
    }

    public Usuario buscarUsuario(Long id) {
        try {
            Usuario usuario = repositorioUsuario.findById(id)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            return usuario;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> buscarUsuarios() {
        try {
            return repositorioUsuario.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar usuários: " + e.getMessage());
        }
    }

    private void mapUsuarioParaAtualizar(Usuario usuarioAtualizado, Usuario usuario) {
        usuarioAtualizado.setNome(usuario.getNome());
        usuarioAtualizado.setNomeSocial(usuario.getNomeSocial());
        usuarioAtualizado.setPerfis(usuario.getPerfis());
        usuarioAtualizado.setTelefones(usuario.getTelefones());
        usuarioAtualizado.setEndereco(usuario.getEndereco());
        usuarioAtualizado.setDocumentos(usuario.getDocumentos());
        usuarioAtualizado.setEmails(usuario.getEmails());
        usuarioAtualizado.setMercadorias(usuario.getMercadorias());
        usuarioAtualizado.setVendas(usuario.getVendas());
        usuarioAtualizado.setVeiculos(usuario.getVeiculos());
    }
}
