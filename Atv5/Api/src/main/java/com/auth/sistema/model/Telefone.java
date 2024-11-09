package com.auth.sistema.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.EntityModel;

@Data
@Entity
public class Telefone extends EntityModel<Telefone> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String ddd;
	@Column(nullable = false)
	private String numero;
}