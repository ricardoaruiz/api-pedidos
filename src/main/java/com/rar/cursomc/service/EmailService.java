package com.rar.cursomc.service;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.rar.cursomc.domain.Cliente;
import com.rar.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido pedido);
	
	void sendEmail(SimpleMailMessage message);
	
	void sendOrderConfirmationHtmlEmail(Pedido pedido);
	
	void sendHtmlEmail(MimeMessage message);

	void sendNewPasswordEmail(Cliente cliente, String newPassword);
}
