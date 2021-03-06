package com.rar.cursomc.security.utils;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {

	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Long expiration;
	
	/**
	 * Generate token
	 * @param username
	 * @return token
	 */
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + this.expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}

	/**
	 * Check if token is valid
	 * @param token
	 * @return boolean
	 */
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date dateNow = new Date(System.currentTimeMillis());
			
			if (username != null && expirationDate != null && dateNow.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get username from token
	 * @param token
	 * @return username
	 */
	public String getUsername(String token) {
		Claims claims = this.getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	/**
	 * Get claims from token
	 * @param token
	 * @return claims
	 */
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes())
				.parseClaimsJws(token).getBody();
			
		} catch(Exception e) {
			return null;
		}
	}
}
