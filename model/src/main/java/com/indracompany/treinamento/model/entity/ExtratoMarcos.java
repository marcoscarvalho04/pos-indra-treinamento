package com.indracompany.treinamento.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "extratomarcos")
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtratoMarcos extends GenericEntity<Long> {



    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "fk_conta_origem_id")
    private ContaBancaria contaOrigem;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "fk_conta_destino_id")
    private ContaBancaria contaDestino;

    @Column
    private String tipoOperacao;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime dataOperacao;

    @Column
    private Double valor;
}
