package com.rar.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.rar.cursomc.domain.Cliente;
import com.rar.cursomc.dto.ClienteDTO;
import com.rar.cursomc.repository.ClienteRespository;
import com.rar.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator extends BaseConstraintValidator<ClienteUpdate, ClienteDTO>{
	
	@Autowired
	private ClienteRespository clienteRepository;
	
	@Override
	public boolean isValid(ClienteDTO cliente, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		this.validateEmail(cliente, list);
		this.buildConstraintsViolations(list, context);
		
		return list.isEmpty();
	}
	
	private void validateEmail(ClienteDTO cliente, List<FieldMessage> list) {
		Integer clienteId = Integer.parseInt(this.getUriParams().get("id"));
		
		Cliente foundCliente = this.clienteRepository.findByEmail(cliente.getEmail());			
		boolean isDiferentCliente = foundCliente != null && !clienteId.equals(foundCliente.getId()); 
		
		if (isDiferentCliente && cliente.getEmail().equals(foundCliente.getEmail())) {
			list.add(new FieldMessage("email", "Email informado já em uso"));
		}
	}

}
