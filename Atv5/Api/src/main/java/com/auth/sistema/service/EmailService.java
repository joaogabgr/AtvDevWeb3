package com.auth.sistema.service;

import com.auth.sistema.model.Email;
import com.auth.sistema.repositories.RepositorioEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

    @Autowired
    private RepositorioEmail repositorioEmail;

    public Email criarEmail(Email email) {
        try {
            return repositorioEmail.save(email);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar email: " + e.getMessage());
        }
    }

    public Email atualizarEmail(Long id, Email email) {
        try {
            Email emailAtualizado = repositorioEmail.findById(id)
                    .orElseThrow(() -> new RuntimeException("Email não encontrado"));
            mapEmailParaAtualizar(emailAtualizado, email);
            return repositorioEmail.save(emailAtualizado);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar email: " + e.getMessage());
        }
    }

    public void deletarEmail(Long id) {
        try {
            repositorioEmail.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar email: " + e.getMessage());
        }
    }

    public Email buscarEmail(Long id) {
        try {
            Email email = repositorioEmail.findById(id)
                    .orElseThrow(() -> new RuntimeException("Email não encontrado"));
            return email;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar email: " + e.getMessage());
        }
    }

    public List<Email> buscarEmails() {
        try {
            return repositorioEmail.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar emails: " + e.getMessage());
        }
    }

    private void mapEmailParaAtualizar(Email emailAtualizado, Email email) {
        emailAtualizado.setEndereco(email.getEndereco());
    }
}
