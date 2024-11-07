package com.auth.jwt.repositories;

import com.auth.jwt.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface RepositorioVenda extends JpaRepository<Venda, Long> {
    List<Venda> findByEmpresaIdAndCadastroBetween(Long id, Date dataInicio, Date dataFim);

}
