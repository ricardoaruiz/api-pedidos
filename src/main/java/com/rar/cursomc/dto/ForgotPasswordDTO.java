package com.rar.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class ForgotPasswordDTO implements Serializable {

	private static final long serialVersionUID = 6637099169109693234L;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message="Email inválido")
	private String email;

	public ForgotPasswordDTO() { /*Nothing*/ }
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
	
}
