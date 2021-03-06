package com.indracompany.treinamento.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaqueDTO implements Serializable{

	private String agencia;
	private String numeroConta;
	private double valor;
	
}
