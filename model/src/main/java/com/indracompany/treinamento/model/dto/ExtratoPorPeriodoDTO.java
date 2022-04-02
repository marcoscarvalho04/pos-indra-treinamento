package com.indracompany.treinamento.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtratoPorPeriodoDTO {

    private LocalDateTime dataOrigem;
    private LocalDateTime dataDestino;
}
