package com.meneez.springboot2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MainApplication implements CommandLineRunner{


	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	
	//usado para teste sempre que iniciar o app rodar
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		

	}
}
