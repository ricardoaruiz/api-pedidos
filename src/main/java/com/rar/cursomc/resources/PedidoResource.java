package com.rar.cursomc.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rar.cursomc.domain.Pedido;
import com.rar.cursomc.dto.PaginacaoDTO;
import com.rar.cursomc.service.PedidoService;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService pedidoService;
	
	@GetMapping(value = "/page")
	public ResponseEntity<Page<Pedido>> listAllPaginated(PaginacaoDTO paginacao) {
		Page<Pedido> listAllPaginated = this.pedidoService.listAllPaginated(paginacao);
		return ResponseEntity.ok(listAllPaginated);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pedido> load(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(this.pedidoService.load(id));
	}
	
	
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody Pedido pedido) {
		Pedido inserted = this.pedidoService.insert(pedido);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(inserted.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
	
}
