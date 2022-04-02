package com.indracompany.treinamento.model.repository;

import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.ExtratoMarcos;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ExtratoMarcosRepository extends GenericCrudRepository<ExtratoMarcos,Long> {

    List<ExtratoMarcos> findAllByDataOperacaoGreaterThanEqualAndDataOperacaoLessThanEqual(LocalDateTime inicio, LocalDateTime fim);
    List<ExtratoMarcos> findByContaOrigem(ContaBancaria contaOrigem);
}
