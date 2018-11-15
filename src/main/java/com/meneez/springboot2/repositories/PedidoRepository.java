package com.meneez.springboot2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meneez.springboot2.domain.Pedido;


/*
 * Acessando os dados(buscar, salvar, alterar e deletar), DAO
 * JpaRepository<Pedido, Integer>, informa os parametros do tipo para consultar nesse caso Pedido, e tambem o objeto identificador da classe(chave primaria) Integer
 */

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	
	

}
