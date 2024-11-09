package com.auth.sistema.model;

import com.auth.sistema.enums.TipoDocumento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;
import jakarta.persistence.Id;
import org.springframework.hateoas.EntityModel;

import java.util.Date;

@Data
@Entity
public class Documento extends EntityModel<Documento> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(nullable = false)
	private TipoDocumento tipo;

	@NotNull
	@Past
	@Column(nullable = false)
	private Date dataEmissao;

	@NotNull
	@Size(min = 1, max = 50) // Ajuste os limites conforme necessário
	@Column(unique = true, nullable = false)
	private String numero;
}
