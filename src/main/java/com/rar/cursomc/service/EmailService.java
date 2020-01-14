package com.rar.cursomc.service;

import org.springframework.mail.SimpleMailMessage;

import com.rar.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage message);
	
}
