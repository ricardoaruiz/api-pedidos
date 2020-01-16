package com.rar.cursomc.security.service;

import org.springframework.security.core.context.SecurityContextHolder;

import com.rar.cursomc.security.domain.User;

public class UserService {

	public static User authenticated() {
		try {
			return (User) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
	
}
