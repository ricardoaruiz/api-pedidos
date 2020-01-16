package com.rar.cursomc.resources;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rar.cursomc.dto.ForgotPasswordDTO;
import com.rar.cursomc.security.domain.User;
import com.rar.cursomc.security.service.UserService;
import com.rar.cursomc.security.utils.JWTUtil;
import com.rar.cursomc.service.AuthService;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Autowired
	private AuthService authService;
	
	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		User user = UserService.authenticated();
		String refreshToken = this.jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + refreshToken);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/forgot")
	public ResponseEntity<Void> forgot(@Valid @RequestBody ForgotPasswordDTO body) {
		authService.sendNewPassword(body.getEmail());
		return ResponseEntity.noContent().build();
	}
	
}
