package com.meneez.springboot2.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.meneez.springboot2.domain.Cliente;
import com.meneez.springboot2.dto.ClienteDTO;
import com.meneez.springboot2.repositories.ClienteRepository;
import com.meneez.springboot2.services.exceptions.DataIntegrityException;
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

	public Cliente find(Integer id) {
		
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
	
	public Cliente update(Cliente obj) {
		//recupera os dados antes de realizar a atualizacao, pois como estou usando dto o objeto vem com alguns atributos nulos
		Cliente newObj = find(obj.getId());
		//Metodo que atualiza o objeto pesquisado na base com os dados recebidos de alteracao
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		//verifica se o id passado existe no banco
		find(id);
		
		//caso tenha produtos associados a categorias não permitir a deleção (DataIntegrityViolationException)
		try {
			repo.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			//Criada uma nova excecao de servico para esse tipo
			throw new DataIntegrityException("Não é possivel excluir porque há entidades relacionadas.");
		}
		
	
	}
	
	public List<Cliente> findAll() {
		//Para que ao listar todas as categorias nao venha todos os produtos associados(no requisito é somente uma lista de 
		//categorias) utiliza-se um DTO (objeto que tem somente os dados que eu necessite para a operacao que utilizo no sistema)
		
		return repo.findAll();
		
	}
	
	//Integer page - informa qual a pagina que se quer
	//Integer linesPerPage - quantas linhas por pagina se quer
	//String orderBy - qual atributo se quer ordenar
	//String direction - Ordenacao vai ser ASC ou DESC
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest =  PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
		
	}
	
	//metodo auxliar que instancia uma cagetoria a partir de um dto, usado por causa do uso do bean de validacao
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
	//Ao utilizar o DTO alguns campos não sao passados como (CPF e Tipo) com isso se eu atualizar o banco de dados com o 
	//objeto puramente esses valores do cliente serão setados para null
	//com isso recupero somente as informações necessarias e atualizo.
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
}
