package com.indracompany.treinamento.controller.rest;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ContaBancariaDTO;
import com.indracompany.treinamento.model.dto.DepositoDTO;
import com.indracompany.treinamento.model.dto.SaqueDTO;
import com.indracompany.treinamento.model.dto.TransferenciaBancariaDTO;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.service.ContaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("rest/contabancaria")
public class ContaBancariaRest extends GenericCrudRest<ContaBancaria, Long, ContaBancariaService> {

    @Autowired
    private ContaBancariaService contaBancariaService;

    @GetMapping("/consultar-saldo/{agencia}/{conta}")
    public ResponseEntity getSaldo(@PathVariable String agencia, @PathVariable String conta) {
        Double saldo;
        try {
            saldo = this.contaBancariaService.consultarSaldo(agencia, conta );

        }catch (AplicacaoException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(saldo,HttpStatus.OK);
    }

    @GetMapping("/consulta-por-cpf/{cpf}")
    public ResponseEntity getContasPorCpf(@PathVariable String cpf) {
        List<ContaBancariaDTO> contaBancariaDTOList = new LinkedList<>();
        try {
            contaBancariaDTOList = this.contaBancariaService.consultarContaPorCpf(cpf);
        }catch (AplicacaoException e) {
            return e.retornarStatusHttp(e.getCustomExceptionValue());
        }
        return new ResponseEntity(contaBancariaDTOList,HttpStatus.OK);
    }

    @PostMapping("/sacar")
    public ResponseEntity sacar(@RequestBody SaqueDTO saqueDTO){

        ContaBancariaDTO contaBancariaDTO;
        try{
            this.contaBancariaService.sacar(saqueDTO);
            contaBancariaDTO =  ContaBancariaDTO.converterEntidadeParaDTO(this.contaBancariaService.consultarConta(saqueDTO.getAgencia(),saqueDTO.getNumeroConta()));
        }catch (AplicacaoException e) {
            return e.retornarStatusHttp(e.getCustomExceptionValue());
        }
        return new ResponseEntity(contaBancariaDTO,HttpStatus.OK);
    }

    @PostMapping("/depositar")
    public ResponseEntity depositar(@RequestBody DepositoDTO depositoDTO) {
        ContaBancariaDTO contaBancariaDTO;

        try {
            if (depositoDTO == null || depositoDTO.getAgencia() == null || depositoDTO.getNumeroConta() == null ) {
                return new ResponseEntity("todos os dados devem estar preenchidos!", HttpStatus.BAD_REQUEST);
            }
            this.contaBancariaService.depositar(depositoDTO);
            contaBancariaDTO =  ContaBancariaDTO.converterEntidadeParaDTO(this.contaBancariaService.consultarConta(depositoDTO.getAgencia(),depositoDTO.getNumeroConta()));
        }catch (AplicacaoException e){
            return e.retornarStatusHttp(e.getCustomExceptionValue());
        }

        return new ResponseEntity(contaBancariaDTO,HttpStatus.OK);
    }

    @PostMapping("/transferir")
    public ResponseEntity transferir(@RequestBody TransferenciaBancariaDTO transferenciaBancariaDTO) {
        List<ContaBancariaDTO> contasAposTransferencia = new LinkedList<>();

        try {
            this.contaBancariaService.transferir(transferenciaBancariaDTO);
        }catch (AplicacaoException e) {
            return e.retornarStatusHttp(e.getCustomExceptionValue());
        }
        return new ResponseEntity(contasAposTransferencia, HttpStatus.OK);
    }


}
