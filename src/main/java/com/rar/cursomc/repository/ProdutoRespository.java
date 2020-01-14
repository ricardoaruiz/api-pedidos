package com.rar.cursomc.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rar.cursomc.domain.Produto;

public interface ProdutoRespository extends JpaRepository<Produto, Integer>{

	@Transactional(readOnly = true)
	@Query(value = "SELECT distinct produto "
			+ "FROM Produto produto INNER JOIN produto.categorias categoria "
			+ "WHERE (:nome IS NULL OR UPPER(produto.nome) LIKE %:nome%) "
			+ "AND ( (:categorias) IS NULL OR categoria.id IN (:categorias) )")		
	Page<Produto> search(
			@Param("nome") String nome, 
			@Param("categorias") List<Integer> categorias, 
			Pageable pageRequest);

}
