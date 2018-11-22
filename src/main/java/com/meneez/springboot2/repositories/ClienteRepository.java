package com.meneez.springboot2.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.meneez.springboot2.domain.Cliente;


/*
 * Acessando os dados(buscar, salvar, alterar e deletar), DAO
 * JpaRepository<Cliente, Integer>, informa os parametros do tipo para consultar nesse caso Cliente, e tambem o objeto identificador da classe(chave primaria) Integer
 */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	
	
	//fazendo uma busca por email, colocando findBy<Campo> o Spring Data já entende a consulta e ja realiza autmoamticamente
	//anotacao @Transactional(readOnly=true) - essa operacao nao necessita ser envolvida como uma transacao de banco de dados, 
	//isso faz ela ficar mais rapida e diminui o locking do gerenciamento de transacoes do banco de dados.
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
	

}
