package com.meneez.springboot2.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.meneez.springboot2.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	
	//banco de dados garante que o email vai ser unico.
	@Column(unique=true)
	private String email;
	private String cpfOuCnpj;
	//uma outra implementacao é armazenar internamente o tipocliente como inteiro e para o mundo externo usar o Enum
	//private TipoCliente tipo;
	private Integer tipo;
	
	
	//Cliente 1 : 1..* Endereco(Um cliente tem 1 ou mais Endereços e um Endereço tem somente 1 cliente)
	//um cliente tem varios endereços
	//mappedBy="cliente" ja mapeado na classe cliente
	//cascade=CascadeType.ALL = Toda alteracao em clientes deve ser refletido em enderecos, se for apagar o cliente vai apagar autmoamticamente os enderecos
	//JsonManagedReference (protegendo a referencia ciclica) = liberando a serializacao de seus enderecos e protegendo a classe cliente de ter referencia ciclica com enderecos, pois o mais importante é cliente 
	//pois cliente tem endereços. Clientes pode serializar os seus endereços, porém o Endereço não pode serializar seus clientes
	//@JsonManagedReference - nao usado por dar bug na app, usado o jsonignore na classe de referencia somente
	@OneToMany(mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Endereco> enderecos = new ArrayList<>();
	
	
	//Cliente 0 : 1..* Telefone (Um cliente tem 1 ou mais telefones, e telefone nao tem Cliente
	//um cliente tem varios telefones
	//como a tabela telefone nao tem id e tem somente um campo do tipo string, considera uma entidada fraca(weak)
	//uma solucao é nao criar uma classe Telefone e usar diretamente aqui
	//para o JPA criar a tabela telefone como uma entidade fraca deve usar a anotacao ElementeCollection
	@ElementCollection
	@CollectionTable(name="telefone")
	private Set<String> telefones = new HashSet<>();
	
	//Pedido N : 1 Cliente (Pedido tem um cliente e Um cliente pode ter ou nao pedidos)
	//Clitem tem uma lista de pedidos
	//Ja mapeado na classe cliente mappedBy="cliente"
	//JsonBackReference - os pedidos de um cliente nao vao ser serializados no json
	//@JsonBackReference - trocado por jsonignore pois esta anotations esta dando erro 
	@JsonIgnore
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	
	public Cliente() {
		
	}


	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		//agora com o uso do DTO o tipo pode ser nulo, entao vou fazer uma condicional
		this.tipo = (tipo==null) ? null : tipo.getCod();
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


	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}


	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}


	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);
	}


	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}


	public List<Endereco> getEnderecos() {
		return enderecos;
	}


	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}


	public Set<String> getTelefones() {
		return telefones;
	}


	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}


	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	
	
	
}
