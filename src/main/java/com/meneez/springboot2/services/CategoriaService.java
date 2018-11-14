package com.meneez.springboot2.services;

import java.util.Optional;

import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meneez.springboot2.domain.Categoria;
import com.meneez.springboot2.repositories.CategoriaRepository;

/**
 * Classe de acesso aos servicos
 * @author mvgmenezes
 *
 */

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository repo;

	public Categoria buscar(Integer id) {
		
		//Acessar os dados atraves do Repositories
		//No Java 8 foi alterado para o objeto Optional e o nome de findOne para findById
		
		
		//Optional<Categoria> obj = repo.findById(id);
		//return obj.orElse(null);
		
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName(), null));

	}
}
