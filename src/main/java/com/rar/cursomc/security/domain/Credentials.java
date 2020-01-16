package com.rar.cursomc.security.domain;

import java.io.Serializable;

public class Credentials implements Serializable {

	private static final long serialVersionUID = 8050387458970865529L;

	private String email;
	
	private String senha;

	public Credentials() { }
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
	
}
