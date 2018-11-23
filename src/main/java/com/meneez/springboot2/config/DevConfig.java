package com.meneez.springboot2.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.meneez.springboot2.services.DBService;
import com.meneez.springboot2.services.email.EmailService;
import com.meneez.springboot2.services.email.SmtpEmailService;

//classe com a configuracao somente para o profile de teste, application-dev.properties
//@Profile("dev") - indico que os beans dessa classe so serao ativos quando o profile developer for usado 
@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DBService dbService;
	
	//recupera o valor da chave spring.jpa.hibernate.ddl-auto=create dentro do application-dev.properties 
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean instanciateDatabase() throws ParseException {
		
		//verifico se a chave spring.jpa.hibernate.ddl-auto=create dentro do application-dev.properties 
		//se esta marcada como create irei executar o carregamento das informacoes basicas
		if (!"create".equals(strategy)) {
			return false;
		}else {
			dbService.instantiateTestDatabase();
		}
		
		
		
		return true;
	}
	
	//Bean criado para retornar uma instancia do SmtpEmailService quando chamado a interface em PedidoService, o Autowired vai procurar
	//uma instancia da interface e vai encontrar esse bean declarado, para o profile=dev.
	@Bean
	public EmailService emailService() {
		return new SmtpEmailService();
	}
}
