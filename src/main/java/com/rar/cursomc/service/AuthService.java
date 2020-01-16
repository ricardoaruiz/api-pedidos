package com.rar.cursomc.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rar.cursomc.domain.Cliente;
import com.rar.cursomc.repository.ClienteRespository;
import com.rar.cursomc.service.exception.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	private Random random = new Random();
	
	public void sendNewPassword(String email) {
		Cliente cliente = clienteService.load(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("Email não encontrado");
		}
		
		String newPassword = newPassword();
		cliente.setSenha(bCryptPasswordEncoder.encode(newPassword));
		
		clienteService.save(cliente);
		
		emailService.sendNewPasswordEmail(cliente, newPassword);
	}

	private String newPassword() {
		char[] vet = new char[10];
		for(int i=0; i<10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = random.nextInt(3);
		
		switch (opt) {
		case 0:
			// digito unicode a partir de 48 com 10
			return (char) (random.nextInt(10) + 48);
		case 1:
			// letra maiuscula unicode a partir de 65 com 26 
			return (char) (random.nextInt(26) + 65);
		case 2:
		default:
			// letra minuscula unicode a partir de 97 com 26
			return (char) (random.nextInt(26) + 97);		
		}
	}
	
}
