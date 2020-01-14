package com.rar.cursomc.service;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SmtpEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(SmtpEmailService.class);
	
	@Autowired
	private MailSender mailSender;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public void sendEmail(SimpleMailMessage message) {
		LOG.info("Enviando email...");
		mailSender.send(message);
		LOG.info("Email enviado com sucesso");		
	}

	@Override
	public void sendHtmlEmail(MimeMessage message) {
		LOG.info("Enviando email...");
		javaMailSender.send(message);
		LOG.info("Email enviado com sucesso");		
	}

}
