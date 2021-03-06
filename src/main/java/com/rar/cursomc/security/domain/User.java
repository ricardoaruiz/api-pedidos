package com.rar.cursomc.security.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rar.cursomc.security.domain.enums.Profile;

public class User implements UserDetails {

	private static final long serialVersionUID = 573745692732325930L;

	private Integer id;
	
	private String email;
	
	private String senha;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public User() { /*Nothing*/ }
	
	public User(Integer id, String email, String senha, Set<Profile> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.authorities = perfis.stream()
				.map(perfil -> new SimpleGrantedAuthority(
						perfil.getDescription())).collect(Collectors.toList());
	}

	public Integer getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasHole(Profile admin) {
		return this.authorities
				.contains(new SimpleGrantedAuthority(admin.getDescription()));
	}
}
