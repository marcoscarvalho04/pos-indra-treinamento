package com.indracompany.treinamento.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtratoMarcosDTO {

    private Long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ContaBancaria contaOrigem;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ContaBancaria contaDestino;

    private String tipoOperacao;
    private LocalDateTime dataOperacao;
    private Double valor;

}
