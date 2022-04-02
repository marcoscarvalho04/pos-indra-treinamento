package com.indracompany.treinamento.model.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cliente extends GenericEntity<Long>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String nome;
	
	@CPF
	@Column(length = 11, unique = true)
	private String cpf;
	
	@Email
	private String email;
	
	private boolean ativo;
	
	private String observacoes;

}
