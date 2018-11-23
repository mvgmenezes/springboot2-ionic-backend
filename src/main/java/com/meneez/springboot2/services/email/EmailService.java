package com.meneez.springboot2.services.email;

import org.springframework.mail.SimpleMailMessage;

import com.meneez.springboot2.domain.Pedido;


public interface EmailService {

	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}
