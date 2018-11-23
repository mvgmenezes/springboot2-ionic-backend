package com.meneez.springboot2.services.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class SmtpEmailService extends AbstractEmailService{

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	//ao dar um inject na classe MailSender ele automaticamente vai buscar as propriedades no aplication-dev.properties
	//e já instancia o objeto com esses atributos
	@Autowired
	private MailSender mailSender;
	
	
	@Override
	public void sendEmail(SimpleMailMessage msg) {
		
		LOG.info("Enviando de email");
		
		mailSender.send(msg);
		
		LOG.info("Email enviado");
	}

}
