package com.indracompany.treinamento.controller.rest;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.model.dto.ExtratoMarcosDTO;
import com.indracompany.treinamento.model.dto.ExtratoPorPeriodoDTO;
import com.indracompany.treinamento.model.entity.ExtratoMarcos;
import com.indracompany.treinamento.model.service.ExtratoMarcosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("rest/extratomarcos")
public class ExtratoMarcosRest extends GenericCrudRest<ExtratoMarcos, Long, ExtratoMarcosService> {

    @Autowired
    private ExtratoMarcosService extratoMarcosService;

    @PostMapping("/por-datas")
    public ResponseEntity getExtratoPorData(@RequestBody ExtratoPorPeriodoDTO extratoPorPeriodoDTO) {
        List<ExtratoMarcosDTO> extratoMarcosDTOList = new LinkedList<>();
        try{
            if (extratoPorPeriodoDTO == null || extratoPorPeriodoDTO.getDataDestino() == null || extratoPorPeriodoDTO.getDataOrigem() == null ) {
                return new ResponseEntity("Necessario preencher todas as datas!", HttpStatus.BAD_REQUEST);
            }
            extratoMarcosDTOList = this.extratoMarcosService.acharPorPeriodo(extratoPorPeriodoDTO.getDataOrigem(), extratoPorPeriodoDTO.getDataDestino());
        }catch (AplicacaoException e) {
            return e.retornarStatusHttp(e.getCustomExceptionValue());
        }
        return new ResponseEntity(extratoMarcosDTOList, HttpStatus.OK);
    }

    @GetMapping("/por-conta/{agencia}/{conta}")
    public ResponseEntity getExtratoPorConta(@PathVariable String agencia, @PathVariable String conta) {
        List<ExtratoMarcosDTO> extratoMarcosDTOList = new LinkedList<>();
        try {
            extratoMarcosDTOList = this.extratoMarcosService.acharPorContaEAgencia(conta,agencia);
        } catch (AplicacaoException e){
            e.retornarStatusHttp(e.getCustomExceptionValue());
        }
        return new ResponseEntity(extratoMarcosDTOList, HttpStatus.OK);
    }

}
