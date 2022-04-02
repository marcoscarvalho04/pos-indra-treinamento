package com.indracompany.treinamento.controller.rest;

import com.indracompany.treinamento.exception.AplicacaoException;
import com.indracompany.treinamento.exception.ExceptionValidacoes;
import com.indracompany.treinamento.model.dto.ClienteDTO;
import com.indracompany.treinamento.model.dto.TransferenciaBancariaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.indracompany.treinamento.model.entity.Cliente;
import com.indracompany.treinamento.model.service.ClienteService;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("rest/clientes")
public class ClienteRest extends GenericCrudRest<Cliente, Long, ClienteService>{
	
	@Autowired
	private ClienteService clienteService;


	@GetMapping("/buscaPorCpf/{cpf}")
	public ResponseEntity getCliente(@PathVariable String cpf ) {
		ClienteDTO clienteDTO = null;
		try {
			clienteDTO = clienteService.buscarClientePorCpf(cpf);
		}catch (AplicacaoException e) {
			return e.retornarStatusHttp(e.getCustomExceptionValue());
		}catch (Exception e ) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity(clienteDTO, HttpStatus.OK);
	}

	@GetMapping("/buscarPorNome/{nome}")
	public ResponseEntity getPorNome(@PathVariable String nome ) {

		List<ClienteDTO> clienteDTO = null;
		try {
			clienteDTO = clienteService.buscarClientePorNome(nome);
		}catch (AplicacaoException e) {
			return e.retornarStatusHttp(e.getCustomExceptionValue());
		}catch (Exception e ){
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<ClienteDTO>>(clienteDTO, HttpStatus.OK);
	}







}
