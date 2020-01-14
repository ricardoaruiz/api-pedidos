package com.rar.cursomc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.rar.cursomc.domain.Produto;
import com.rar.cursomc.dto.PaginacaoDTO;
import com.rar.cursomc.dto.ProdutoDTO;
import com.rar.cursomc.repository.ProdutoRespository;
import com.rar.cursomc.service.exception.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRespository produtoRespository;
		
	public Produto load(Integer id) {
		return this.produtoRespository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Recurso não existente com Id " + id + ", Nome: " + Produto.class.getName()));
	}
	
	public Page<ProdutoDTO> listPaginated(
			String nome, 
			List<Integer> categoriasId, 
			PaginacaoDTO paginacao) {
						
		Page<Produto> pageProdutos = this.produtoRespository.search(
				StringUtils.isEmpty(nome) ? null: nome.toUpperCase(), 
				categoriasId.isEmpty() ? null : categoriasId, 
				paginacao.buildRequestPage());
		
		return pageProdutos.map(produto -> new ProdutoDTO(produto));
		
	}
	
}
