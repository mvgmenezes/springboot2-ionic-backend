package com.meneez.springboot2;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.meneez.springboot2.domain.Categoria;
import com.meneez.springboot2.repositories.CategoriaRepository;

@SpringBootApplication
public class MainApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	
	//usado para teste sempre que iniciar o app rodar
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		//salvando no banco de dados
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
	}
}
