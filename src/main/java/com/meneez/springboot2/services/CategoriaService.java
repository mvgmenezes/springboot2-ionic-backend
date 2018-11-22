package com.meneez.springboot2.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meneez.springboot2.domain.Categoria;
import com.meneez.springboot2.repositories.CategoriaRepository;
import com.meneez.springboot2.services.exceptions.ObjectNotFoundException;

/**
 * Classe de acesso aos servicos
 * @author mvgmenezes
 *
 */

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		
		//Acessar os dados atraves do Repositories
		//No Java 8 foi alterado para o objeto Optional e o nome de findOne para findById
		
		
		//Optional<Categoria> obj = repo.findById(id);
		//return obj.orElse(null);
		
		
		// usando o lambda para substituir o bloco abaixo:
		//if (obj == null) {
		//	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
		//	+ ", Tipo: " + Categoria.class.getName());
		//}
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName(), null));

	}
	
	public Categoria insert(Categoria obj) {
		//garantindo que é um insert pois se o id estiver preenchido o jpa trata como um update
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		
		//verifica se o id passado existe no banco
		find(obj.getId());
		return repo.save(obj);
	}
}
