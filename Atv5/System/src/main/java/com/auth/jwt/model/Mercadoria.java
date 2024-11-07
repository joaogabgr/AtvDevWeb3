package com.auth.jwt.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.EntityModel;

@Data
@Entity
public class Mercadoria extends EntityModel<Mercadoria> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date validade;
	@Column(nullable = false)
	private Date fabricao;
	@Column(nullable = false)
	private Date cadastro;
	@Column(nullable = false)
	private String nome;
	@Column(nullable = false)
	private long quantidade;
	@Column(nullable = false)
	private double valor;
	@Column()
	private String descricao;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
}