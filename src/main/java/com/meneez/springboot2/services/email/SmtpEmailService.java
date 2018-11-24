package com.meneez.springboot2.services.email;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	//ao dar um inject na classe MailSender ele automaticamente vai buscar as propriedades no aplication-dev.properties
	//e já instancia o objeto com esses atributos
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		
		LOG.info("Enviando de email");
		
		mailSender.send(msg);
		
		LOG.info("Email enviado");
	}


	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando de email html");
		
		javaMailSender.send(msg);
		
		LOG.info("Email enviado");
		
	}

}
