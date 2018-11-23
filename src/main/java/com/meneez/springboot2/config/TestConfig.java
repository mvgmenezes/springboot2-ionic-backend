package com.meneez.springboot2.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.meneez.springboot2.services.DBService;
import com.meneez.springboot2.services.email.EmailService;
import com.meneez.springboot2.services.email.MockEmailService;

//classe com a configuracao somente para o profile de teste, application-test.properties
//@Profile("test") - indico que os beans dessa classe so serao ativos quando o profile test for usado 
@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		
		dbService.instantiateTestDatabase();
		
		return true;
	}
	
	
	//Bean criado para retornar uma instancia do MockEmailService quando chamado a interface em PedidoService, o Autowired vai procurar
	//uma instancia da interface e vai encontrar esse bean declarado.
	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}
}
