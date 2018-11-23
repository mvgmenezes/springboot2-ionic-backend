package com.meneez.springboot2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meneez.springboot2.domain.PagamentoComBoleto;
import com.meneez.springboot2.domain.PagamentoComCartao;

//codigo padrao para classes abstratas e o registro das classes que herdam a classe pagamento, aqui é feito o registro das subclasses
//permite a instancia da subclasse

@Configuration
public class JacksonConfig {
	
	// https://stackoverflow.com/questions/41452598/overcome-can-not-construct-instance-of-interfaceclass-without-hinting-the-pare

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder() {
			
			public void configure(ObjectMapper objectMapper) {
				objectMapper.registerSubtypes(PagamentoComCartao.class); 
				objectMapper.registerSubtypes(PagamentoComBoleto.class); 
				super.configure(objectMapper);
			}
			
		};
		
		return builder;
		
		
	}
}