package com.meneez.springboot2.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.meneez.springboot2.domain.Categoria;


/*
 * Acessando os dados(buscar, salvar, alterar e deletar), DAO
 * JpaRepository<Categoria, Integer>, informa os parametros do tipo para consultar nesse caso Categoria, e tambem o objeto identificador da classe(chave primaria) Integer
 */

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	
	

}
