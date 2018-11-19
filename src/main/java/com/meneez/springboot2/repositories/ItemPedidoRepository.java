package com.meneez.springboot2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meneez.springboot2.domain.ItemPedido;


/*
 * Acessando os dados(buscar, salvar, alterar e deletar), DAO
 * JpaRepository<ItemPedido, Integer>, informa os parametros do tipo para consultar nesse caso ItemPedido, e tambem o objeto identificador da classe(chave primaria) Integer
 */

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {
	
	

}
