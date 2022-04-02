package com.indracompany.treinamento.model.repository;

import com.indracompany.treinamento.model.entity.Cliente;

import java.util.List;

public interface ClienteRepository extends GenericCrudRepository<Cliente, Long>{

    Cliente findByCpf(String cpf);

    List<Cliente> findByNomeContains(String nome);

}
