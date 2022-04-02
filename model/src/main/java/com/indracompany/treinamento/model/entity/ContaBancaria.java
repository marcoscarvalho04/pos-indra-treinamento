package com.indracompany.treinamento.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "contas")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContaBancaria extends GenericEntity<Long>{

    private static final long serialVersionUID = -5824703733929187165L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4, nullable = false)
    private String agencia;

    @Column(length = 6, nullable = false)
    private String numero;

    @Column(nullable = false)
    private double saldo;

    @ManyToOne
    @JoinColumn(name = "fk_cliente_id")
    private Cliente cliente;
}
