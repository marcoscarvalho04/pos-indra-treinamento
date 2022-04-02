package com.indracompany.treinamento.model.service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.*;
import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.entity.ContaBancaria;
import com.indracompany.treinamento.model.entity.ExtratoMarcos;
import com.indracompany.treinamento.model.repository.ContaBancariaRepository;
import com.indracompany.treinamento.util.CpfUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;


@Service
public class ContaBancariaService extends GenericCrudService<ContaBancaria,Long, ContaBancariaRepository> {


    private static final String TIPO_SAQUE = "SAQUE";

    private static final String TIPO_DEPOSITO = "DEPOSITO";

    private static final String TIPO_TRANSFERENCIA = "TRANSFERENCIA";

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ExtratoMarcosService extratoMarcosService;

    public Double consultarSaldo(String agencia, String numero) {

        try {

            ContaBancaria contaBancaria = this.consultarConta(agencia,numero);
            return contaBancaria.getSaldo();

        } catch(AplicacaoException e) {

            throw e;

        }


    }

    public ContaBancaria consultarConta(String agencia, String numero) {

        ContaBancaria contaBancaria = repository.findByAgenciaAndNumero(agencia, numero);

        if (contaBancaria == null) {
            throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
        }

        return contaBancaria;
    }

    public List<ContaBancariaDTO> consultarContaPorCpf(String cpf){

        List<ContaBancariaDTO> contas = new LinkedList<>();
        try {

            ClienteDTO cliente = this.clienteService.buscarClientePorCpf(cpf);
            Cliente clienteElement = this.clienteService.converterDTOParaEntidade(cliente);
            List<ContaBancaria> todasAsContas = repository.findByCliente(clienteElement);
            contas.addAll(ContaBancariaDTO.converterEntidadeParaDTO(todasAsContas));

        }catch (AplicacaoException e) {
            throw e;
        }

    if (contas.isEmpty()) {
        throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
    }

    return contas;

    }

    public ContaBancaria depositar(DepositoDTO depositoDTO){

        ContaBancaria contaDeposito;

        if (depositoDTO.getValor() <= 0){
            throw new AplicacaoException(ExceptionValidacoes.ERRO_DEPOSITO_INVALIDO);
        }

        try {

            contaDeposito = this.consultarConta(depositoDTO.getAgencia(), depositoDTO.getNumeroConta());
            contaDeposito.setSaldo(contaDeposito.getSaldo() + depositoDTO.getValor());
            super.salvar(contaDeposito);
            this.salvarExtrato(contaDeposito,depositoDTO.getValor(),TIPO_DEPOSITO);

        }catch (AplicacaoException e) {
            throw e;
        }

        return contaDeposito;

    }

    public ContaBancaria sacar(SaqueDTO saqueDTO) {
        ContaBancaria contaSaque;

        if (saqueDTO.getValor() <= 0) {
            throw new AplicacaoException(ExceptionValidacoes.ERRO_SAQUE_INVALIDO);
        }
        try {

            contaSaque = this.consultarConta(saqueDTO.getAgencia(),saqueDTO.getNumeroConta());
            if (contaSaque.getSaldo() < saqueDTO.getValor()) {
                throw new AplicacaoException(ExceptionValidacoes.ERRO_SAQUE_SALDO_INSUFICIENTE);
            }
            contaSaque.setSaldo(contaSaque.getSaldo() - saqueDTO.getValor());

            this.salvarExtrato(contaSaque, saqueDTO.getValor(), TIPO_SAQUE);
            super.salvar(contaSaque);

        }catch (AplicacaoException e ) {
            throw e;
        }

        return contaSaque;
    }

    public void salvarExtrato(ContaBancaria contaSaque, Double valor, String tipo ){

        ExtratoMarcos extratoMarcos = ExtratoMarcos.builder()
                .contaOrigem(contaSaque)
                .tipoOperacao(tipo)
                .dataOperacao(LocalDateTime.now())
                .valor(valor)
                .build();

        this.extratoMarcosService.salvar(extratoMarcos);
    }


    public void salvarExtrato(ContaBancaria contaOrigem,ContaBancaria contaDestino, Double valor,  String tipo ){

        ExtratoMarcos extratoMarcos = ExtratoMarcos.builder()
                .contaOrigem(contaOrigem)
                .contaDestino(contaDestino)
                .tipoOperacao(tipo)
                .dataOperacao(LocalDateTime.now())
                .valor(valor)
                .build();

        this.extratoMarcosService.salvar(extratoMarcos);
    }

    @Transactional(rollbackFor = {Exception.class, Throwable.class})
    public void transferir(TransferenciaBancariaDTO transferenciaBancariaDTO){

        SaqueDTO saqueDTO = SaqueDTO.builder()
                .agencia(transferenciaBancariaDTO.getAgenciaOrigem())
                .numeroConta(transferenciaBancariaDTO.getNumeroContaOrigem())
                .valor(transferenciaBancariaDTO.getValor())
                .build();
        DepositoDTO depositoDTO = DepositoDTO.builder()
                .agencia(transferenciaBancariaDTO.getAgenciaDestino())
                .numeroConta(transferenciaBancariaDTO.getNumeroContaDestino())
                .valor(transferenciaBancariaDTO.getValor())
                .build();

        ContaBancaria contaOrigem = this.sacar(saqueDTO);
        ContaBancaria contaDestino = this.depositar(depositoDTO);
        this.salvarExtrato(contaOrigem, contaDestino, transferenciaBancariaDTO.getValor(), TIPO_TRANSFERENCIA);

    }
}
