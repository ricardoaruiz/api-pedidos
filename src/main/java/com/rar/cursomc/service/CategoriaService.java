package com.rar.cursomc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.rar.cursomc.domain.Categoria;
import com.rar.cursomc.dto.CategoriaDTO;
import com.rar.cursomc.dto.PaginacaoDTO;
import com.rar.cursomc.repository.CategoriaRepository;
import com.rar.cursomc.service.exception.DataIntegrityException;
import com.rar.cursomc.service.exception.ObjectNotFoundException;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	/**
	 * List all categorias
	 * @return List of categorias
	 */
	public List<CategoriaDTO> listAll() {	
		List<Categoria> categorias = categoriaRepository.findAll();
		
		return categorias.stream()
				.map(categoria -> 
					new CategoriaDTO(categoria)).collect(Collectors.toList());
	}
	
	/**
	 * List all categorias paginated
	 * @param page
	 * @param linesPerPage
	 * @param orderBy
	 * @param direction
	 * @return Paginated list of categorias
	 */
	public Page<CategoriaDTO> listAllPaginated(PaginacaoDTO paginacao) {		
		Page<Categoria> categorias = this.categoriaRepository.findAll(paginacao.buildRequestPage());
		return categorias.map(item -> new CategoriaDTO(item));
	}
	
	/**
	 * Save a new Categoria
	 * @param categoria
	 * @return Saved Categoria
	 */
	public CategoriaDTO save(CategoriaDTO categoriaDTO) {
		Categoria categoria = this.fromDTO(categoriaDTO);
		categoria.setId(null);
		return new CategoriaDTO(categoriaRepository.save(categoria));		
	}
	
	/**
	 * Load a Categoria by id
	 * @param id
	 * @return Categoria
	 */
	public CategoriaDTO load(Integer id) {
		Optional<Categoria> optionalCategoria = categoriaRepository.findById(id);
		
		return optionalCategoria.map(item -> new CategoriaDTO(item))
				.orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado! ID: " + id + " Tipo: " + Categoria.class.getName()));
	}

	/**
	 * Update a categgoria
	 * @param categoria
	 */
	public void update(CategoriaDTO categoriaDTO) {		
		Categoria categoria = this.fromDTO(categoriaDTO);
		CategoriaDTO loadedCategoria = this.load(categoria.getId());
		this.updateData(loadedCategoria, categoria);
		categoriaRepository.save(categoria);		
	}

	/**
	 * Remove a categoria by id
	 * @param id
	 */
	public void delete(Integer id) {
		try {
			this.load(id);
			this.categoriaRepository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException(
					"Não é possível remover a Categoria com id " + id + 
					" pois a mesma possui produtos associados. " + 
					"Nome: " + Categoria.class.getName());
		}
	}

	private Categoria fromDTO(CategoriaDTO categoriaDTO) {
		return new Categoria(categoriaDTO.getId(), categoriaDTO.getNome());
	}
	
	private void updateData(CategoriaDTO loadedCategoria, Categoria categoria) {
		loadedCategoria.setNome(categoria.getNome());		
	}

}
