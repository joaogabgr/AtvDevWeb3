package com.auth.sistema.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.EntityModel;

@Data
@Entity
public class Endereco extends EntityModel<Endereco> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String estado;
	@Column(nullable = false)
	private String cidade;
	@Column(nullable = false)
	private String bairro;
	@Column(nullable = false)
	private String rua;
	@Column(nullable = false)
	private String numero;
	@Column(nullable = false)
	private String codigoPostal;
	@Column()
	private String informacoesAdicionais;
}