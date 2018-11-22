package com.meneez.springboot2.dto;

import java.io.Serializable;

import com.meneez.springboot2.domain.Categoria;

//Para que ao listar todas as categorias nao venha todos os produtos associados(no requisito é somente uma lista de 
//categorias) utiliza-se um DTO (objeto que tem somente os dados que eu necessite para a operacao que utilizo no sistema)
public class CategoriaDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
		
	}
	
	public CategoriaDTO(Categoria obj) {
		id = obj.getId();
		nome = obj.getNome();
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
	
	
	
	

}
