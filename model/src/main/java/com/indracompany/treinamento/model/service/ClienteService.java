package com.indracompany.treinamento.model.service;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ClienteDTO;
import com.indracompany.treinamento.util.CpfUtil;
import org.glassfish.jersey.internal.guava.Lists;
import org.springframework.stereotype.Service;

import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.repository.ClienteRepository;

import java.util.LinkedList;
import java.util.List;

@Service
public class ClienteService extends GenericCrudService<Cliente, Long, ClienteRepository>{

    public List<ClienteDTO> buscarClientePorCpf(String cpf){

        if (!CpfUtil.validaCPF(cpf)) {
            throw new AplicacaoException(ExceptionValidacoes.ERRO_CPF_INVALIDO);
        }

        List<Cliente> cliente = repository.findByCpf(cpf);

        List<ClienteDTO> clienteDTOList = new LinkedList<ClienteDTO>();

        if (cliente == null || cliente.isEmpty()) {
            throw new AplicacaoException(ExceptionValidacoes.ALERTA_NENHUM_REGISTRO_ENCONTRADO);
        }
        for (Cliente clienteElemento : cliente) {

            clienteDTOList.add(ClienteDTO.builder()
                    .cpf(clienteElemento.getCpf())
                    .nome(clienteElemento.getNome())
                    .id(clienteElemento.getId())
                    .email(clienteElemento.getEmail())
                    .build());

        }

        return clienteDTOList;
    }

	  
}
