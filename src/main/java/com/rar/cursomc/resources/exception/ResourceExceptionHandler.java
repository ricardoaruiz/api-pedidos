package com.rar.cursomc.resources.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rar.cursomc.security.exception.AuthorizationException;
import com.rar.cursomc.service.exception.DataIntegrityException;
import com.rar.cursomc.service.exception.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e) {
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND.value())
				.body(getStandardError(e, HttpStatus.NOT_FOUND));
	}
	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e) {		
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST.value())
				.body(getStandardError(e, HttpStatus.BAD_REQUEST));		
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardError> methodArgumentNotValid(MethodArgumentNotValidException e) {
		return ResponseEntity
				.status(HttpStatus.BAD_REQUEST.value())
				.body(getValidationError(e));
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<StandardError> authorization(AuthorizationException e) {
		return ResponseEntity
				.status(HttpStatus.FORBIDDEN.value())
				.body(getStandardError(e, HttpStatus.FORBIDDEN));
	}	
		
	private StandardError getStandardError(Exception e, HttpStatus httpStatus) {
		return new StandardError(
				httpStatus.value(), 
				e.getMessage(), 
				System.currentTimeMillis());
	}
	
	private StandardError getValidationError(MethodArgumentNotValidException e) {
		
		StandardError validationError = this.getStandardError(e, HttpStatus.BAD_REQUEST);
		validationError.setMsg("Erro de validação");
		
		e.getBindingResult().getFieldErrors()
			.stream().forEach(fieldError -> {
				validationError.addError(
						fieldError.getField(), 
						fieldError.getDefaultMessage());
			});
		
		return validationError;
		
	}

}
