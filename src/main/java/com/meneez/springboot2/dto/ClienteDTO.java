package com.meneez.springboot2.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.meneez.springboot2.domain.Cliente;
import com.meneez.springboot2.services.validation.ClienteInsert;
import com.meneez.springboot2.services.validation.ClienteUpdate;
//Anotacao criada para realizar a validacao no update, se o email enviado para ser atualizado já existe em outro usuario
@ClienteUpdate
public class ClienteDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Nao declarar o cpf, pois o usuario nao pode atualizar esse campo
	private Integer id;
	
	@NotEmpty(message="Preechimento obrigatório")
	@Length(min=5, max=120, message="O tamanho deve ser entre 5 e 120 caracteres")
	private String nome;
	
	@NotEmpty(message="Preechimento obrigatório")
	@Email(message="Email invalido")
	private String email;
	
	public ClienteDTO() {
		
	}
	
	public ClienteDTO (Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
