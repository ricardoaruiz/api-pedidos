package com.rar.cursomc.domain.enums;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");
	
	private Integer id;
	private String descricao;
	
	private Perfil(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}
	
	public static Perfil toEnum(Integer id) {
		for(Perfil perfil: Perfil.values()) {
			if (perfil.id.equals(id)) {
				return perfil;
			}
		}
		
		throw new IllegalStateException("O Perfil informado não existe");
	}

	public Integer getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
