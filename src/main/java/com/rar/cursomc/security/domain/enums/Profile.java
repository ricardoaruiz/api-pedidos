package com.rar.cursomc.security.domain.enums;

public enum Profile {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENT(2, "ROLE_CLIENTE");
	
	private Integer id;
	private String description;
	
	private Profile(Integer id, String descricao) {
		this.id = id;
		this.description = descricao;
	}
	
	public static Profile toEnum(Integer id) {
		for(Profile perfil: Profile.values()) {
			if (perfil.id.equals(id)) {
				return perfil;
			}
		}
		
		throw new IllegalStateException("O Perfil informado não existe");
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
	
}
