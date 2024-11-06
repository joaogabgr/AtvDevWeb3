package com.auth.jwt.model;


import jakarta.persistence.*;
import lombok.Data;

import org.springframework.hateoas.EntityModel;

@Data
@Entity
public class Email extends EntityModel<Email> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private String endereco;
}