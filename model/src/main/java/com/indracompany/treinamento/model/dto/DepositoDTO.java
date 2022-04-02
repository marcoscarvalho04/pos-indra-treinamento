package com.indracompany.treinamento.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepositoDTO implements Serializable{

	private String agencia;
	private String numeroConta;
	private double valor;
	
}
