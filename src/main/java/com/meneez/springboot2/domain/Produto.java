package com.meneez.springboot2.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3831815958228043407L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	
	//o produto tem varias categorias
	//como o produto tem varias categorias e categoria tem varios produtos é uma relacionamento de muitos para muitos (N : N)
	//com isso preciso informar que é uma relacionamento de many to many
	//JoinTable que informa as chaves estrangeiras
	//joinColumns informar o nome de referencia dentro de produtos e inversejoinColumns informa o nome de referencia na outra tabela categoria
	//@JsonBackReference - para resolver a referencia ciclica, no outro objeto Produto ja informei que JsonManagedReference logo 
	//o compilador ja sabe para nao executar ciclico.
	//@JsonBackReference - trocado por jsonignore pois esta anotations esta dando erro 
	@JsonIgnore
	@ManyToMany
	@JoinTable(name= "PRODUTO_CATEGORIA", 
				joinColumns = @JoinColumn(name = "produto_id"),
				inverseJoinColumns = @JoinColumn(name="categoria_id")
	)
	private List<Categoria> categorias = new ArrayList<>();
	
	
	//Produto conhece os itens associados a ele
	//em ItemPedidoPK foi relaizado a associacao ManytoOne de produto, aqui deve ser feito a associacao inversa 
	//usando o id.produto, onde id é da classe ItemPedido e .produto é da classe ItemPedidoPK
	//JsonIgnore - nao é importante saber a partir de um produto seus itens, pois é mais interessante saber a partir de uma lista de itens seus produtos
	@JsonIgnore
	@OneToMany(mappedBy="id.produto")
	private Set<ItemPedido> itens = new HashSet<>();
	
	public Produto() {
		
	}

	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}
	
	//Um produto conhece os pedidos dele.
	//JsonIgnore - nao é importante saber a partir de um produto seus itens, pois é mais interessante saber a partir de uma lista de itens seus produtos
	@JsonIgnore
	public List<Pedido> getPedidos(){
		
		List<Pedido> lista = new ArrayList<>();
		
		for (ItemPedido x : itens) {
			lista.add(x.getPedido());
		}
		
		return lista;
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}
	
	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
	
	

	
	
}
