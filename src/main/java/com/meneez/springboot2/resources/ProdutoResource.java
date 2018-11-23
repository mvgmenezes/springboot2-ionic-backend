package com.meneez.springboot2.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meneez.springboot2.domain.Produto;
import com.meneez.springboot2.dto.ProdutoDTO;
import com.meneez.springboot2.resources.util.URL;
import com.meneez.springboot2.services.ProdutoService;

/**
 * Primeira chamda da url vem por aqui depois vai para sevices depois para repositories e o pacote Domain é os VO
 * @author mvgmenezes
 *
 */

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {
	
	@Autowired
	private ProdutoService service;
	
	//exemplo: http://localhost:8080/produtos/2
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		
		//Foi criado um Handler (ResourceExceptionHandler.class) que é um objeto que vai interceptar o bloco caso ocorra uma excecao,
		//do tipo definido por mim ObjectNotFondException (caso nao encontrou nenhum objeto com o id enviado)
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}
	
	
	//usando page, alterando a url para listar todas para http://localhost:8080/produtos/?nome=or&categorias=1,4
	//passando os argumento via parametro(opcionais) se usa @RequestParam
	//http://localhost:8080/produtos/?nome=or&categorias=1,4
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction){
		
		
		//convertendo a string endcode do parametro enviado para o decode correto de String
		String nomeDecoded = URL.decodeParam(nome);
		
		//convertendo os parametros dos ids enviados para o formato do method
		List<Integer> ids = URL.decodeIntList(categorias);
		

		Page<Produto> list = service.search(nomeDecoded, ids, page,linesPerPage, orderBy, direction);
		
		//Como a classe Page ja é um Java 8 complience nao precisa do stream() e nem do collect(Collectors.toList())
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listDto);

	}

}
