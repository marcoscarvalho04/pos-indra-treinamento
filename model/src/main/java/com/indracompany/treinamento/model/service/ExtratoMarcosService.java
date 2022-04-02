package com.indracompany.treinamento.model.service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ExtratoMarcosDTO;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.ExtratoMarcos;
import com.indracompany.treinamento.model.repository.ExtratoMarcosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExtratoMarcosService extends GenericCrudService<ExtratoMarcos,Long, ExtratoMarcosRepository>{

    @Autowired
    public ContaBancariaService contaBancariaService;


    public List<ExtratoMarcosDTO> acharPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        List<ExtratoMarcos> extrato = repository.findAllByDataOperacaoGreaterThanEqualAndDataOperacaoLessThanEqual(inicio, fim);
        List<ExtratoMarcosDTO> extratoDTO = new LinkedList<>();
        if (extrato == null || extrato.isEmpty()) {
            throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
        }
        extratoDTO = this.converterEntidadeParaDTO(extrato);
        return extratoDTO;
    }

    public List<ExtratoMarcosDTO> acharPorContaEAgencia(String conta, String agencia) {
        try {
            ContaBancaria contaBancaria = this.contaBancariaService.consultarConta(agencia,conta);
            List<ExtratoMarcos> extratoMarcosList = this.repository.findByContaOrigem(contaBancaria);
            return this.converterEntidadeParaDTO(extratoMarcosList);
        }catch (AplicacaoException e ) {
            throw e;
        }
    }

    private List<ExtratoMarcosDTO> converterEntidadeParaDTO(List<ExtratoMarcos> extrato){
        List<ExtratoMarcosDTO> extratoDTO = new LinkedList<>();
        for (ExtratoMarcos extratoMarcos : extrato) {
            extratoDTO.add(
                    ExtratoMarcosDTO.builder()
                            .id(extratoMarcos.getId())
                            .contaDestino(extratoMarcos.getContaDestino())
                            .contaOrigem(extratoMarcos.getContaOrigem())
                            .dataOperacao(extratoMarcos.getDataOperacao())
                            .tipoOperacao(extratoMarcos.getTipoOperacao())
                            .valor(extratoMarcos.getValor())
                            .build()
            );
        }
        return extratoDTO;
    }
}
