package com.rar.cursomc.service.exception;

public class DataIntegrityException extends RuntimeException {

	private static final long serialVersionUID = 142129417897742797L;

	public DataIntegrityException() {
		super();
	}

	public DataIntegrityException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DataIntegrityException(String message, Throwable cause) {
		super(message, cause);
	}

	public DataIntegrityException(String message) {
		super(message);
	}

	public DataIntegrityException(Throwable cause) {
		super(cause);
	}	
	
}
