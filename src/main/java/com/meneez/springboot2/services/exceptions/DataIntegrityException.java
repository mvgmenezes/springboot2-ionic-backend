package com.meneez.springboot2.services.exceptions;

public class DataIntegrityException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataIntegrityException (String msg) {
		super(msg);
	}
	
	//Throwable cause = É o objeto da causa de algo que aconteceu antes
	public DataIntegrityException (String msg, Throwable cause) {
		super(msg, cause);
	}

}
