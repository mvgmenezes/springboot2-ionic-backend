package com.meneez.springboot2.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.meneez.springboot2.domain.Categoria;
import com.meneez.springboot2.dto.CategoriaDTO;
import com.meneez.springboot2.services.CategoriaService;

/**
 * Primeira chamda da url vem por aqui depois vai para sevices depois para repositories e o pacote Domain é os VO
 * @author mvgmenezes
 *
 */

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaService service;
	
	//exemplo: http://localhost:8080/categorias/2
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		//Foi criado um Handler (ResourceExceptionHandler.class) que é um objeto que vai interceptar o bloco caso ocorra uma excecao,
		//do tipo definido por mim ObjectNotFondException (caso nao encontrou nenhum objeto com o id enviado)
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}
	
	//@RequestBody - Faz o json ser convertido para o objeto java automaticamente
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj){

		obj = service.insert(obj);
		//retornando a uri com o id inserido
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		//codigo 201 é retornando quando um novo recurso é inserido
		return ResponseEntity.created(uri).build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id){
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		//Para que ao listar todas as categorias nao venha todos os produtos associados(no requisito é somente uma lista de 
		//categorias) utiliza-se um DTO (objeto que tem somente os dados que eu necessite para a operacao que utilizo no sistema)
		
		List<Categoria> list = service.findAll();
		
		//convertendo um objeto Categoria para Categoria DTO, utilizo o map(que executa uma operacao para cada elemento da lista, nesse caso executa o construtor)
		//apos isso uso o collectors para converter novamente em uma lista.
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);

	}
	
	//usando page, alterando a url para listar todas para http://localhost:8080/categorias/page
	//passando os argumento via parametro(opcionais) se usa @RequestParam
	//http://localhost:8080/categorias/page?linesPerPage=3&page=1&direction=DESC
	@RequestMapping(value="/page" , method=RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction){

		Page<Categoria> list = service.findPage(page,linesPerPage, orderBy, direction);
		
		//Como a classe Page ja é um Java 8 complience nao precisa do stream() e nem do collect(Collectors.toList())
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		
		return ResponseEntity.ok().body(listDto);

	}

}
