package com.rar.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rar.cursomc.service.DBService;
import com.rar.cursomc.service.EmailService;
import com.rar.cursomc.service.MockEmailService;

@Configuration
@Profile("test")
public class TesteConfig {

	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instantiateDatabase() throws ParseException {		
		dbService.instantiateTestDatabase();		
		return true;
	}
	
//	@Bean
//	public EmailService emailService() {
//		return new MockEmailService();
//	}
	
}
