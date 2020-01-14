package com.rar.cursomc.service;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.rar.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {
	
	private static final Logger LOG = LoggerFactory.getLogger(AbstractEmailService.class);
	
	@Value("${email.default.sender}")
	private String sender;
	
	@Autowired
	private TemplateEngine templateEngine;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	/**
	 * Send a plain text pedido confirmation
	 */
	@Override
	public void sendOrderConfirmationEmail(Pedido pedido) {
		SimpleMailMessage message = this.prepareSimpleMailMessageFromPedido(pedido);
		this.sendEmail(message);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido pedido) {
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(pedido.getCliente().getEmail());
		message.setFrom(sender);
		message.setSubject("Pedido confirmado. Código: " + pedido.getId());
		message.setSentDate(new Date(System.currentTimeMillis()));
		message.setText(pedido.toString());
		
		return message;
	}

	/**
	 * Send a html pedido confirmation
	 */
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido pedido) {
		try {
			MimeMessage message = this.prepareMimeMessageFromPedido(pedido);
			this.sendHtmlEmail(message);		
		} catch (MessagingException e) {
			LOG.warn("Erro ao enviar o email no formato email. Enviando o email no formato text");
			this.sendOrderConfirmationEmail(pedido);
		}
	}	
	
	protected MimeMessage prepareMimeMessageFromPedido(Pedido pedido) throws MessagingException {
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
		
		messageHelper.setTo(pedido.getCliente().getEmail());
		messageHelper.setFrom(sender);
		messageHelper.setSubject("Pedido confirmado. Código: " + pedido.getId());
		messageHelper.setSentDate(new Date(System.currentTimeMillis()));
		messageHelper.setText(htmlFromPedido(pedido), true);
		
		return message;
	}

	/**
	 * Populate email template with pedido
	 * @param pedido
	 * @return
	 */
	protected String htmlFromPedido(Pedido pedido) {
		Context context = new Context();
		context.setVariable("pedido", pedido);		
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	
}
