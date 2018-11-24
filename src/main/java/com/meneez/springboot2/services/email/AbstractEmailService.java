package com.meneez.springboot2.services.email;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.meneez.springboot2.domain.Pedido;

//classe abstrata (Template Method) que implementa o email service
public abstract class AbstractEmailService implements EmailService {
	
	@Value("${default.sender}")
	private String sender;
	
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
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
	
	
	//Usando mensagem html no email
	
	//pega o template thymeleaf, injeta o objeto pedido dentro, processa o template e retorna o html em string
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context context = new Context();
		//envia o objeto pedido para o template(resources/templates/email/confirmacaoPedido.html) poder povoar os dados no html
		context.setVariable("pedido", obj);
		
		//Processar o template para retornar em string, por padrao o thymeleaf busca dentro da pasta resources/templates/
		return templateEngine.process("email/confirmacaoPedido", context);
			
	}

	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		}catch(MessagingException e) {
			//se ocorrer um errro no envio do email html vou chamar o texto plano
			sendOrderConfirmationEmail(obj);
		}
		
	}

	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		
		//instanciando o mime message
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);
		mmh.setTo(obj.getCliente().getEmail());
		mmh.setFrom(sender);
		mmh.setSubject("Pedido Confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true); //true para indicar que o conteudo é um html

		return mimeMessage;
	}
}
