package com.meneez.springboot2.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.meneez.springboot2.domain.Categoria;
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

}
