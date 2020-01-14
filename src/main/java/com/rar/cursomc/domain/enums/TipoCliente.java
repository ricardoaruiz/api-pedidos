package com.rar.cursomc.domain.enums;

public enum TipoCliente {

	PESSOA_FISICA(1, "Pessoa Física"),
	PESSOA_JURIDICA(2, "Pessoa Jurídica");
	
	private Integer id;
	private String descricao;

	private TipoCliente(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}	

	public static TipoCliente toEnum(Integer id) {
		
		if (id == null) {
			return null;
		}
		
		for(TipoCliente tc : TipoCliente.values()) {
			if (tc.getId().equals(id)) {
				return tc;
			}
		}
		
		throw new IllegalArgumentException("Código do Tipo Pessoa inválido");
	}
	
}
