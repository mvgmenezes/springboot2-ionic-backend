package com.meneez.springboot2.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
public class Pedido implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	
	//Pedido 1 : 1 Pagamento (Um Pedido tem Um Pagamento)	
	//Um para Um Pedido e Pagamento
	////o id do pagamento é o mesmo id do pedido correspondente (relacionamento de 1 para 1 no banco de dados)
	//cascade=CascadeType.ALL ja foi realizada a referencia na classe pagamento e aqui deve ser feito o relacionamento bidirecional(mappedBy="pedido")
	//@JsonManagedReference - Pagamento pode ser serializado (evitando a referencia ciclica)
	//@JsonManagedReference - nao usado por dar bug na app, usado o jsonignore na classe de referencia somente
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento;
	
	//Pedido N : 1 Cliente (Pedido tem um cliente e Um cliente pode ter ou nao pedidos)
	//Relacionamento bidirecional precisa fazer a referencia em cliente
	//Um pedido tem um cliente
	//JsonManagedReference - (referencia ciclica) o cliente de um pedido será serailziado
	//@JsonManagedReference - nao usado por dar bug na app, usado o jsonignore na classe de referencia somente
	@ManyToOne
	@JoinColumn(name="cliente_id") //nome da chave estrangeira na tabela de Pedido
	private Cliente cliente;
	
	//Pedido 0 : 1 (Um Pedido sempre tem um Endereco)
	//relacionamento DIrecional, nao tem que fazer a referencia em Endereco, pois um Pedido que tem um endereço e Endereço nao tem relacionamento com pedido.
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")//nome da chave estrangeira na tabela de Pedido
	private Endereco enderecoDeEntrega;
	
	public Pedido() {
		
	}

	//Pedido conhece os itens associados a ele 
	//em ItemPedidoPK foi relaizado a associacao ManytoOne de pedido, aqui deve ser feito a associacao inversa 
	//usando o id.pedido, onde id é da classe ItemPedido e .pedido é da classe ItemPedidoPK
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	

	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega
			) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
		//nem sempre um pedido tem um pagamento de imediato
		//this.pagamento = pagamento;
	}

	//criando um metodo para calcular o total do pedido, ao utilizar o 'get'na frente do metodo o json será apresentado 
	public double getValorTotal() {
		double soma = 0.0;
		for (ItemPedido ip : itens){
			soma = soma + ip.getSubTotal();
		}
		return soma;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}
	
	
	public Cliente getCliente() {
		return cliente;
	}



	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}



	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	
	
	

}
