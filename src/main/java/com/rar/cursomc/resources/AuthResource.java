package com.rar.cursomc.resources;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rar.cursomc.security.domain.User;
import com.rar.cursomc.security.service.UserService;
import com.rar.cursomc.security.utils.JWTUtil;

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;
	
	@PostMapping(value = "/refresh_token")
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		User user = UserService.authenticated();
		String refreshToken = this.jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + refreshToken);
		return ResponseEntity.noContent().build();
	}
	
}
