package com.rar.cursomc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rar.cursomc.domain.Produto;
import com.rar.cursomc.dto.PaginacaoDTO;
import com.rar.cursomc.dto.ProdutoDTO;
import com.rar.cursomc.service.ProdutoService;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService produtoService;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Produto> load(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(this.produtoService.load(id));
	}
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<ProdutoDTO>> list(
			@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") List<Integer> categorias,
			PaginacaoDTO paginacao
		) {
			
		Page<ProdutoDTO> pageCategorias = this.produtoService.listPaginated(
				nome, 
				categorias, 
				paginacao);		
		
		return ResponseEntity.ok(pageCategorias);		
	}
	
}
