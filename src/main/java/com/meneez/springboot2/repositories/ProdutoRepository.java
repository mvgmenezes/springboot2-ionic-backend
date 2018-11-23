package com.meneez.springboot2.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.meneez.springboot2.domain.Categoria;
import com.meneez.springboot2.domain.Produto;


/*
 * Acessando os dados(buscar, salvar, alterar e deletar), DAO
 * JpaRepository<Categoria, Integer>, informa os parametros do tipo para consultar nesse caso Categoria, e tambem o objeto identificador da classe(chave primaria) Integer
 */

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
	
	//por ser um metodo fora do padrao de nomes do framework spring, deve ser implementado usando a anotacao @Query
	//a anotacao @Param coloca o valor passado no metodo para dentro da query (:nome)
	@Transactional(readOnly=true)
	@Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categorias")
	Page<Produto> search(@Param("nome") String nome, @Param("categorias") List<Categoria> categorias, Pageable pageRequest);
	
	//pode-se usar a mesma consulta anterior utilizando o padrao de nomeclatura do spring sem a necessidade do JPQL
	@Transactional(readOnly=true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn(String nome, List<Categoria> categorias, Pageable pageRequest);

}
