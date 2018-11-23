package com.meneez.springboot2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.meneez.springboot2.domain.Categoria;
import com.meneez.springboot2.domain.Produto;
import com.meneez.springboot2.repositories.CategoriaRepository;
import com.meneez.springboot2.repositories.ProdutoRepository;
import com.meneez.springboot2.services.exceptions.ObjectNotFoundException;

/**
 * Classe de acesso aos servicos
 * @author mvgmenezes
 *
 */

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		
		//Acessar os dados atraves do Repositories
		//No Java 8 foi alterado para o objeto Optional e o nome de findOne para findById
		
		
		//Optional<Produto> obj = repo.findById(id);
		//return obj.orElse(null);
		
		
		// usando o lambda para substituir o bloco abaixo:
		//if (obj == null) {
		//	throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
		//	+ ", Tipo: " + Produto.class.getName());
		//}
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName(), null));

	}
	
	//implemetando o metodo do caso de uso: 
	//2. O cliente informa um trecho de nome de produto desejado, e seleciona as categorias desejadas
	//3. O sistema informa nome e preço dos produtos que se enquadram na pesquisa.
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		
		//recupera todas as categorias pelos ids
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		
		//return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
		return repo.search(nome, categorias, pageRequest);
		
	}
}
