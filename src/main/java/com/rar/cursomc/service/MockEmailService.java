package com.rar.cursomc.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);
	
	@Override
	public void sendEmail(SimpleMailMessage message) {
		LOG.info("Simulando o envio de email");
		LOG.info(message.toString());
		LOG.info("Email enviado com sucesso");		
	}

	@Override
	public void sendHtmlEmail(MimeMessage message) {
		LOG.info("Simulando o envio de email no formato HTML");
		LOG.info(message.toString());
		LOG.info("Email no formato HTML enviado com sucesso");		
	}

}
