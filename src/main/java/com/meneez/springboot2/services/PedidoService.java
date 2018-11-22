package com.meneez.springboot2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meneez.springboot2.domain.Pedido;
import com.meneez.springboot2.repositories.PedidoRepository;
import com.meneez.springboot2.services.exceptions.ObjectNotFoundException;

/**
 * Classe de acesso aos servicos
 * @author mvgmenezes
 *
 */

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	public Pedido find(Integer id) {
		
		//Acessar os dados atraves do Repositories
		//No Java 8 foi alterado para o objeto Optional e o nome de findOne para findById
		
		
		//Optional<Pedido> obj = repo.findById(id);
		//return obj.orElse(null);
		
		
		// usando o lambda para substituir o bloco abaixo:
		//if (obj == null) {
		//	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
		//	+ ", Tipo: " + Pedido.class.getName());
		//}
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName(), null));

	}
}
