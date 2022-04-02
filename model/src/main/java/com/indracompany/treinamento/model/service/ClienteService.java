package com.indracompany.treinamento.model.service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ClienteDTO;
import com.indracompany.treinamento.util.CpfUtil;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Service;

import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.repository.ClienteRepository;

import javax.swing.tree.ExpandVetoException;
import java.util.LinkedList;
import java.util.List;


@Service
public class ClienteService extends GenericCrudService<Cliente, Long, ClienteRepository>{

    public ClienteDTO buscarClientePorCpf(String cpf){

        if (!CpfUtil.validaCPF(cpf)) {
            throw new AplicacaoException(ExceptionValidacoes.ERRO_CPF_INVALIDO);
        }

        Cliente cliente = repository.findByCpf(cpf);


        return converterEntidadeParaDTO(cliente);
    }

    public List<ClienteDTO> buscarClientePorNome(String nome) {

        List<Cliente> clienteList = repository.findByNomeContains(nome);
        return converterEntidadeParaDTO(clienteList);

    }

    @Override
    public Cliente salvar(Cliente cliente){

        Cliente existente = repository.findByCpf(cliente.getCpf());

        if (existente != null) {
            throw new AplicacaoException(ExceptionValidacoes.ERRO_CONTA_DUPLICADA);
        }

        return super.salvar(cliente);
    }

    public ClienteDTO converterEntidadeParaDTO(Cliente cliente) {
        if (cliente == null){
            throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
        }
        return ClienteDTO.builder()
                .email(cliente.getEmail())
                .cpf(cliente.getCpf())
                .nome(cliente.getNome())
                .id(cliente.getId())
                .build();
    }

    public List<ClienteDTO> converterEntidadeParaDTO(List<Cliente> clienteList) {
        List<ClienteDTO> clienteDTOList = new LinkedList<ClienteDTO>();

        if (clienteList == null || clienteList.isEmpty()) {
            throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
        }
        for (Cliente clienteElemento : clienteList) {

            clienteDTOList.add(ClienteDTO.builder()
                    .cpf(clienteElemento.getCpf())
                    .nome(clienteElemento.getNome())
                    .id(clienteElemento.getId())
                    .email(clienteElemento.getEmail())
                    .build());

        }
        return clienteDTOList;
    }

    public List<Cliente> converterDTOParaEntidade(List<ClienteDTO> clienteDTOList) {

        List<Cliente> clienteList = new LinkedList<>();

        if (clienteDTOList == null || clienteDTOList.isEmpty()) {
            throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
        }

        for (ClienteDTO clienteDTO: clienteDTOList) {
            clienteList.add(
                Cliente.builder()
                        .cpf(clienteDTO.getCpf())
                        .email(clienteDTO.getEmail())
                        .nome(clienteDTO.getNome())
                        .build()
            );
        }
        return clienteList;

    }

    public Cliente converterDTOParaEntidade(ClienteDTO clienteDTO) {
        return Cliente.builder()
                .id(clienteDTO.getId())
                .nome(clienteDTO.getNome())
                .cpf(clienteDTO.getCpf())
                .email(clienteDTO.getEmail())
                .build();
    }

	  
}
