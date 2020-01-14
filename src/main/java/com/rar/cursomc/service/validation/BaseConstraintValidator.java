package com.rar.cursomc.service.validation;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.rar.cursomc.resources.exception.FieldMessage;

public abstract class BaseConstraintValidator<A extends Annotation, V> implements ConstraintValidator<A, V>{

	@Autowired
	private HttpServletRequest request;
	
	protected void buildConstraintsViolations(List<FieldMessage> list, ConstraintValidatorContext context) {
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
	}
	
	@SuppressWarnings("unchecked")
	protected Map<String, String> getUriParams() {
		return (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
	}
		
}
