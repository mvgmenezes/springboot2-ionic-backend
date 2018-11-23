package com.meneez.springboot2.services.email;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.meneez.springboot2.domain.Pedido;

//classe abstrata (Template Method) que implementa o email service
public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	
	
	
	//(Template Method) em metodos abstratos, pode se chamar outros metodos (sendEmail(sm);) que nao foram implementados ainda mas pode utilizar
	//na utilizacao da classe abstrada, esse é o padrao de projetos Template Method, deixando capaz de implementar um metodo baseado 
	//em um metodo abstrato que depois vão ser implementados pelas implementacoes da interface.
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	//protected - as subclasses poderao acessar esse metodo
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());
		sm.setFrom(this.sender);
		sm.setSubject("Pedido Confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());
		return sm;
	}

}
