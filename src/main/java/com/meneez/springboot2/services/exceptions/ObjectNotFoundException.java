package com.meneez.springboot2.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ObjectNotFoundException (String msg) {
		super(msg);
	}
	
	//Throwable cause = É o objeto da causa de algo que aconteceu antes
	public ObjectNotFoundException (String msg, Throwable cause) {
		super(msg, cause);
	}

}
