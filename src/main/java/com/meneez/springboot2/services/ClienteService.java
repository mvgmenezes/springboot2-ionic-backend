package com.meneez.springboot2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meneez.springboot2.domain.Cliente;
import com.meneez.springboot2.repositories.ClienteRepository;
import com.meneez.springboot2.services.exceptions.ObjectNotFoundException;

/**
 * Classe de acesso aos servicos
 * @author mvgmenezes
 *
 */

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {
		
		//Acessar os dados atraves do Repositories
		//No Java 8 foi alterado para o objeto Optional e o nome de findOne para findById
		
		
		//Optional<Cliente> obj = repo.findById(id);
		//return obj.orElse(null);
		
		
		// usando o lambda para substituir o bloco abaixo
		//if (obj == null) {
		//	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
		//	+ ", Tipo: " + Cliente.class.getName());
		//}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName(), null));

	}
}
