package com.rar.cursomc.dto;

import java.io.Serializable;

import com.rar.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {

	private static final long serialVersionUID = 1741133795684069338L;

	private Integer id;
	
	private String nome;
	
	private Double preco;

	public ProdutoDTO() { /*Nothing*/ }
	
	public ProdutoDTO(Produto produto) {
		this(produto.getId(), produto.getNome(), produto.getPreco());
	}
	
	public ProdutoDTO(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}	
	
}
