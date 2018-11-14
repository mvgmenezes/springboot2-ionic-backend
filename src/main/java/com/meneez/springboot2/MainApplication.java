package com.meneez.springboot2;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.meneez.springboot2.domain.Categoria;
import com.meneez.springboot2.domain.Cidade;
import com.meneez.springboot2.domain.Estado;
import com.meneez.springboot2.domain.Produto;
import com.meneez.springboot2.repositories.CategoriaRepository;
import com.meneez.springboot2.repositories.CidadeRepository;
import com.meneez.springboot2.repositories.EstadoRepository;
import com.meneez.springboot2.repositories.ProdutoRepository;

@SpringBootApplication
public class MainApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	
	//usado para teste sempre que iniciar o app rodar
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		//Categoria pode ter varios produtos 
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		//Produtos pode ter varias categorias tambem
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//salvando no banco de dados
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		
		//salvando os produtos
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		
		//criando os estados
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		//primiero deve salvar os estados pois ele é referencia em outra tabela
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
				
		
		
	}
}
