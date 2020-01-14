package com.rar.cursomc.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rar.cursomc.dto.CategoriaDTO;
import com.rar.cursomc.dto.PaginacaoDTO;
import com.rar.cursomc.service.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService categoriaService;
	
	/**
	 * List all categorias
	 * @return List of categorias
	 */
	@GetMapping	
	public ResponseEntity<List<CategoriaDTO>> listAll() {
		return ResponseEntity.ok(categoriaService.listAll());
	}
	
	/**
	 * List all paginated categorias
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return list of categorias paginated
	 */
	@GetMapping(value = "/page")
	public ResponseEntity<Page<CategoriaDTO>> listAllPaginated(PaginacaoDTO paginacao) {
		
		return ResponseEntity.ok(
				this.categoriaService.listAllPaginated(paginacao));
	}
	
	/**
	 * Load a categoria bt id
	 * @param id
	 * @return Categoria
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<CategoriaDTO> load(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(categoriaService.load(id));
	}
	
	/**
	 * Create a new categoria
	 * @param categoria
	 * @return Void
	 */
	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody CategoriaDTO categoria) {
		categoria = this.categoriaService.save(categoria);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(categoria.getId()).toUri();
		
		return ResponseEntity.created(uri).build();				
	}
	
	/**
	 * Update a categoria
	 * @param id
	 * @param categoria
	 * @return Void
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(
			@PathVariable("id") Integer id,
			@Valid @RequestBody CategoriaDTO categoria) {
		
		categoria.setId(id);
		this.categoriaService.update(categoria);
		return ResponseEntity.noContent().build();		
	}
	
	/**
	 * Remove a categoria
	 * @param id
	 * @return Void
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		this.categoriaService.delete(id);
		return ResponseEntity.noContent().build();				
	}
	
	
}
