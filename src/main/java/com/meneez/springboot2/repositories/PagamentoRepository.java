package com.meneez.springboot2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meneez.springboot2.domain.Pagamento;


/*
 * Acessando os dados(buscar, salvar, alterar e deletar), DAO
 * JpaRepository<Pagamento, Integer>, informa os parametros do tipo para consultar nesse caso Pagamento, e tambem o objeto identificador da classe(chave primaria) Integer
 */

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer> {
	
	

}
