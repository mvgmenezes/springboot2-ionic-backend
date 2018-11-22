package com.meneez.springboot2.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meneez.springboot2.domain.Cliente;
import com.meneez.springboot2.dto.ClienteDTO;
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
	
	//incluindo um bean de validacao @Valid e alterando o Cliente por um ClienteDTO pois contem os mapeamentos das validacoes 
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id){
		
		//Convertendo o DTO para o objeto Cliente
		Cliente obj = service.fromDTO(objDTO);
		
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
	public ResponseEntity<List<ClienteDTO>> findAll() {

		//Para que ao listar todas as categorias nao venha todos os produtos associados(no requisito é somente uma lista de 
		//categorias) utiliza-se um DTO (objeto que tem somente os dados que eu necessite para a operacao que utilizo no sistema)
		
		List<Cliente> list = service.findAll();
		
		//convertendo um objeto Cliente para Cliente DTO, utilizo o map(que executa uma operacao para cada elemento da lista, nesse caso executa o construtor)
		//apos isso uso o collectors para converter novamente em uma lista.
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listDto);

	}
	
	//usando page, alterando a url para listar todas para http://localhost:8080/clientes/page
	//passando os argumento via parametro(opcionais) se usa @RequestParam
	//http://localhost:8080/clientes/page?linesPerPage=3&page=1&direction=DESC
	@RequestMapping(value="/page" , method=RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction){

		Page<Cliente> list = service.findPage(page,linesPerPage, orderBy, direction);
		
		//Como a classe Page ja é um Java 8 complience nao precisa do stream() e nem do collect(Collectors.toList())
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		
		return ResponseEntity.ok().body(listDto);

	}


}
