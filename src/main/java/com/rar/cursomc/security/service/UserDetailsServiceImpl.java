package com.rar.cursomc.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rar.cursomc.domain.Cliente;
import com.rar.cursomc.repository.ClienteRespository;
import com.rar.cursomc.security.domain.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ClienteRespository clienteRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if (cliente == null) {
			throw new UsernameNotFoundException(email);
		}
		
		return new User(
				cliente.getId(), 
				cliente.getEmail(), 
				cliente.getSenha(), 
				cliente.getPerfis());
	}

}
