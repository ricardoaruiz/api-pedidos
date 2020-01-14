package com.rar.cursomc.service.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.rar.cursomc.domain.Cliente;
import com.rar.cursomc.domain.enums.TipoCliente;
import com.rar.cursomc.dto.ClienteNewDTO;
import com.rar.cursomc.repository.ClienteRespository;
import com.rar.cursomc.resources.exception.FieldMessage;
import com.rar.cursomc.service.validation.utils.BR;

public class ClienteInsertValidator extends BaseConstraintValidator<ClienteInsert, ClienteNewDTO> {
	
	@Autowired
	private ClienteRespository clienteRepository;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		
		this.validateTipoPessoa(objDto, list);
		this.validateUniqueEmail(objDto, list);
		this.validateCpfOrCnpj(objDto, list);
		this.buildConstraintsViolations(list, context);
		
		return list.isEmpty();
	}
	
	private void validateUniqueEmail(ClienteNewDTO objDto, List<FieldMessage> list) {
		Cliente clienteFound = this.clienteRepository.findByEmail(objDto.getEmail());
		if (clienteFound != null) {
			list.add(new FieldMessage("email", "Email informado já em uso"));
		}
	}
	
	private void validateTipoPessoa(ClienteNewDTO objDto, List<FieldMessage> list) {
		try {
			TipoCliente.toEnum(objDto.getTipo());
		} catch (IllegalArgumentException e) {
			list.add(new FieldMessage("tipo", "O tipo de pessoa informado não é válido"));
		}
	}
	
	private void validateCpfOrCnpj(ClienteNewDTO objDto, List<FieldMessage> list) {
		if (objDto.getTipo() == TipoCliente.PESSOA_FISICA.getId() && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		if (objDto.getTipo() == TipoCliente.PESSOA_JURIDICA.getId() && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}		
	}
	
}