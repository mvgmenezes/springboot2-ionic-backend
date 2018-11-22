package com.meneez.springboot2.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meneez.springboot2.domain.Cliente;
import com.meneez.springboot2.services.ClienteService;

/**
 * Primeira chamda da url vem por aqui depois vai para sevices depois para repositories e o pacote Domain é os VO
 * @author mvgmenezes
 *
 */

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	//exemplo: http://localhost:8080/categorias/2
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		
		//Foi criado um Handler (ResourceExceptionHandler.class) que é um objeto que vai interceptar o bloco caso ocorra uma excecao,
		//do tipo definido por mim ObjectNotFondException (caso nao encontrou nenhum objeto com o id enviado)
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}

}
