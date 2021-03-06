package com.meneez.springboot2.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.meneez.springboot2.domain.enums.EstadoPagamento;

//@Inheritance = mapeando a heranca, pode ser realizado de duas maneira:
//1- InheritanceType.SINGLE_TABLE - criar uma unica tabela no banco de dados com os dados de PagamentoComBoleto e PagamentoComCartao e na recuperacao desses
//itens um objeto vem preenchido e o outro null - (Usado quando tem poucos atributos na subclasse)
//2 - InheritanceType.JOINED - Outra estrategia é criar no banco de dados uma tabela para cada subclasse, uma tabela de Pagamento, uma tabela de PagamentoComBoleto e
//uma tabela de PagamentoComCartao e quando pesquisar os pagamento deve ser feito o join das tabelas - (Usado quando tem muitos atribuitos na subclasse)
//@JsonTypeInfo como é uma classe abstrada preciso permitir a instanciacao de subclasses a partir dojson para incluir um tipo de pagamento, 
//essa anotacao informa que a minha classe vai ter um campo adicional com o nome @type, Quando for enviar um objeto do tipo da superclasse (Pagamento), inclua o campo adicional para indicar qual das subclasses deve ser instanciada:
@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class Pagamento implements Serializable {
//classe definida como abstract para garantir que nao se possa instanciar uma classe do tipo pagamento e somente 
//suas subclasses
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//o id do pagamento é o mesmo id do pedido correspondente (relacionamento de 1 para 1 no banco de dados)
	@Id
	private Integer id;
	private Integer estado;
	
	//Pedido 1 : 1 Pagamento (Um Pedido tem Um Pagamento)	
	//o id do pagamento é o mesmo id do pedido correspondente (relacionamento de 1 para 1 no banco de dados)
	//@JsonBackReference - nao permitindo a serilizacao desse componente (evitando a referencia ciclica)
	//@JsonBackReference - trocado por jsonignore pois esta anotations esta dando erro 
	@JsonIgnore
	@OneToOne
	@JoinColumn(name="pedido_id") //chave estrangeira na tabela de Pagamento
	@MapsId
	private Pedido pedido;
	
	
	
	public Pagamento() {
		
	}

	
	public Pagamento(Integer id, EstadoPagamento estado, Pedido pedido) {
		super();
		this.id = id;
		this.estado = (estado==null) ? null : estado.getCod();
		this.setPedido(pedido);
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EstadoPagamento getEstado() {
		return EstadoPagamento.toEnum(estado);
	}

	public void setEstado(EstadoPagamento estado) {
		this.estado = estado.getCod();
	}

	public Pedido getPedido() {
		return pedido;
	}


	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
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
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
	
	
	
	
}
