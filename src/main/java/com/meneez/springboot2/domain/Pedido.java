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
@Entity
public class Pedido implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Date instante;
	
	
		
	//Um para Um Pedido e Cliente
	////o id do pagamento é o mesmo id do pedido correspondente (relacionamento de 1 para 1 no banco de dados)
	//cascade=CascadeType.ALL ja foi realizada a referencia na classe pagamento e aqui deve ser feito o relacionamento bidirecional(mappedBy="pedido")
	@OneToOne(cascade=CascadeType.ALL, mappedBy="pedido")
	private Pagamento pagamento;
	
	//Relacionamento bidirecional precisa fazer a referencia em cliente
	//Um pedido tem um cliente
	@ManyToOne
	@JoinColumn(name="cliente_id") //nome da chave estrangeira na tabela de Pedido
	private Cliente cliente;
	
	//relacionamento DIrecional, nao tem que fazer a referencia em Endereco
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
