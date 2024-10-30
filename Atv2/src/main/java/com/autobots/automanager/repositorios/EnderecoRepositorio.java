package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnderecoRepositorio extends JpaRepository<Endereco, Long> {
}
