package com.meneez.springboot2.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cidade implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	//Cidade * : 1 Estado (Uma cidade tem somente 1 estado e um Estado tem varias cidades)
	//Cidade tem um estado (no caso de ja ser uma cidade cadastrada)
	//ou seja Muitas cidades(many) para um estado(toOne)
	//como é na tabela cidade que será criado a fk estado_id, aqui é o principal mapeamento
	//@JsonManagedReference = Evitando referencia ciclica - Cidade pode serializar seus estados, mas estados nao pode serializar suas cidades, por conta do negocio e ser mais importante
	//Cidade conhecer seus estados.
	//@JsonManagedReference - nao usado por dar bug na app, usado o jsonignore na classe de referencia somente
	@ManyToOne
	@JoinColumn(name="estado_id")
	private Estado estado;
	
	public Cidade() {
		
	}

	public Cidade(Integer id, String nome, Estado estado) {
		super();
		this.id = id;
		this.nome = nome;
		this.estado = estado;
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

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cidade other = (Cidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
