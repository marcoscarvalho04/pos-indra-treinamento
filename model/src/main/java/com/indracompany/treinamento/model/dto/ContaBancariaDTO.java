package com.indracompany.treinamento.model.dto;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import lombok.Builder;
import lombok.Data;
import org.glassfish.jersey.internal.guava.Lists;

import java.util.LinkedList;
import java.util.List;

@Data
@Builder
public class ContaBancariaDTO {

    private String agencia;
    private String numero;
    private double saldo;
    private Cliente cliente;


    public static ContaBancariaDTO converterEntidadeParaDTO(ContaBancaria contaBancaria) {
        if (contaBancaria == null) {
            throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
        }
        return ContaBancariaDTO.builder()
                .cliente(contaBancaria.getCliente())
                .saldo(contaBancaria.getSaldo())
                .numero(contaBancaria.getNumero())
                .build();
    }
    public static List<ContaBancariaDTO> converterEntidadeParaDTO(List<ContaBancaria> contas) {

        if (contas== null || contas.isEmpty()) {
            throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
        }
        List<ContaBancariaDTO> contaBancariaDTO = new LinkedList<>();
        for(ContaBancaria contaBancaria: contas){
            contaBancariaDTO.add(ContaBancariaDTO.builder()
                    .agencia(contaBancaria.getAgencia())
                    .numero(contaBancaria.getNumero())
                    .saldo(contaBancaria.getSaldo())
                    .cliente(contaBancaria.getCliente())
                    .build());
        }
        return contaBancariaDTO;

    }


}
