package com.rar.cursomc.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rar.cursomc.domain.Cliente;
import com.rar.cursomc.dto.ClienteDTO;
import com.rar.cursomc.dto.ClienteNewDTO;
import com.rar.cursomc.dto.PaginacaoDTO;
import com.rar.cursomc.service.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;
	
	/**
	 * List all clientes
	 * @return list of clientes
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping
	public ResponseEntity<List<Cliente>> listAll() {
		return ResponseEntity.ok(this.clienteService.listAll());
	}
	
	/**
	 * List all paginated clientes
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return paginated list of clientes
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping(value = "/page")
	public ResponseEntity<Page<Cliente>> listAllPaginated(PaginacaoDTO paginacao) {
		
		Page<Cliente> listAllPaginated = this.clienteService.listAllPaginated(paginacao);
		
		return ResponseEntity.ok(listAllPaginated);
	}
	
	/**
	 * Load a cliente by id
	 * @param id
	 * @return Cliente
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Cliente> load(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(this.clienteService.load(id));
	}
	
	/*
	 * Create a new Cliente
	 */
	@PostMapping
	public ResponseEntity<Void> create(@Valid @RequestBody ClienteNewDTO cliente) {
		Integer clienteId = this.clienteService.save(cliente);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(clienteId).toUri();
		
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Update a cliente
	 * @param id
	 * @param cliente
	 * @return Void
	 */	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Void> update(
			@PathVariable("id") Integer id, 
			@Valid @RequestBody ClienteDTO cliente) {
		
		cliente.setId(id);
		this.clienteService.update(cliente);
		return ResponseEntity.noContent().build();
	}
	
	/**
	 * Remove a cliente
	 * @param id
	 * @return Void
	 */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		this.clienteService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
}
