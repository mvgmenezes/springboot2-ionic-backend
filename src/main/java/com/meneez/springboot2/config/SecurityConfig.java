package com.meneez.springboot2.config;


import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.meneez.springboot2.security.JWTAuthenticationFilter;
import com.meneez.springboot2.security.JWTAuthorizationFilter;
import com.meneez.springboot2.security.JWTUtil;


//implementando o JWT 

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//para liberar o acesso do browser ao repositorio h2
	@Autowired
	private Environment env;
	
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtil jWTUtil;
	
	//acesso publico
	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"		
	};
	
	//acesso publico para somente recuperar os dados (GET) e nao para fazer o post e put
	private static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/clientes/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		//para liberar o acesso do browser ao repositorio h2
		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			//permitindo acessar o h2
			http.headers().frameOptions().disable();
		}
		
		
		//configurandoo cors e desabilitando o csrf(usado para quem armazena a o token na sessao)
		http.cors().and().csrf().disable();
		//libera o acesso para as urls publicas e para os outros solicita autenticacao
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();
		
		//usando o filter para validar se o usuario e senha sao validos
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jWTUtil));
		//usando o filter para validar se o token é valido
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jWTUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	
	//usado no spring security para configurar
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	}
	
	//criando um bean de criptografia 
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
