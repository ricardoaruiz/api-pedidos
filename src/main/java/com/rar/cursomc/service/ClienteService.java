package com.rar.cursomc.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rar.cursomc.domain.Cidade;
import com.rar.cursomc.domain.Cliente;
import com.rar.cursomc.domain.Endereco;
import com.rar.cursomc.domain.enums.TipoCliente;
import com.rar.cursomc.dto.ClienteDTO;
import com.rar.cursomc.dto.ClienteNewDTO;
import com.rar.cursomc.dto.PaginacaoDTO;
import com.rar.cursomc.repository.CidadeRepository;
import com.rar.cursomc.repository.ClienteRespository;
import com.rar.cursomc.repository.EnderecoRespository;
import com.rar.cursomc.service.exception.DataIntegrityException;
import com.rar.cursomc.service.exception.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRespository clienteRespository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRespository enderecoRespository;
	
	/**
	 * List all clientes
	 * @return List of clientes
	 */
	public List<Cliente> listAll() {
		return this.clienteRespository.findAll();
	}
	
	/**
	 * List all paginated clientes
	 * @param page
	 * @param size
	 * @param orderBy
	 * @param direction
	 * @return Paginated list of clientes
	 */
	public Page<Cliente> listAllPaginated(PaginacaoDTO paginacao) {		
		return this.clienteRespository.findAll(paginacao.buildRequestPage());		
	}
	
	/**
	 * Load a cliente
	 * @param id
	 * @return Cliente
	 */
	public Cliente load(Integer id) {
		return this.clienteRespository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("O objeto com ID " + id + " não foi encontrado, Tipo " + Cliente.class.getName()));
	}
	
	/**
	 * Create a new cliente
	 * @param cliente
	 */
	@Transactional
	public Integer save(ClienteNewDTO clienteNew) {
		Cliente cliente = this.fromDTO(clienteNew);
		cliente = this.clienteRespository.save(cliente);
		this.enderecoRespository.saveAll(cliente.getEnderecos());
		return cliente.getId();
	}	

	/**
	 * Update a cliente
	 * @param cliente
	 */
	public void update(ClienteDTO cliente) {
		Cliente loadedCliente = this.load(cliente.getId());
		Cliente newCliente = this.fromDTO(cliente);
		this.updateData(loadedCliente, newCliente);
		this.clienteRespository.save(loadedCliente);
	}

	/**
	 * Remove a cliente
	 * @param id
	 */
	public void delete(Integer id) {
		try {
			this.load(id);
			this.clienteRespository.deleteById(id);
		} catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("O Cliente com id " + 
					id + " não pode ser removido pois pedidos relacionados, " + 
					"Nome: " + Cliente.class.getName());
		}
	}
	
	private void updateData(Cliente loadedCliente, Cliente cliente) {
		loadedCliente.setNome(cliente.getNome());
		loadedCliente.setEmail(cliente.getEmail());		
	}
	
	private Cliente fromDTO(ClienteDTO cliente) {
		return new Cliente(cliente.getNome(), cliente.getEmail(), null, null);
	}
	
	private Cliente fromDTO(ClienteNewDTO clienteNew) {
		
		Cliente cliente = new Cliente(
				clienteNew.getNome(), 
				clienteNew.getEmail(), 
				clienteNew.getCpfOuCnpj(),
				TipoCliente.toEnum(clienteNew.getTipo()));

		Optional<Cidade> cidadeOptional = cidadeRepository.findById(clienteNew.getCidade());
				
		Endereco endereco = new Endereco(
				clienteNew.getLogradouro(), 
				clienteNew.getNumero(), 
				clienteNew.getComplemento(),
				clienteNew.getBairro(), 
				clienteNew.getCep(), 
				cliente, 
				cidadeOptional.orElse(null));
		
		cliente.getEnderecos().add(endereco);
		
		cliente.setTelefones(
				clienteNew.getTelefones().stream()
					.map(telefone -> telefone).collect(Collectors.toSet()));
		
		return cliente;
	}
		
}