package com.auth.jwt.model;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.EntityModel;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Credencial extends EntityModel<Credencial> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private Date criacao;
	@Column()
	private Date ultimoAcesso;
	@Column(nullable = false)
	private boolean inativo;
}