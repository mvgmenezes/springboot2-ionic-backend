package com.meneez.springboot2.resources.exception;

import java.util.ArrayList;
import java.util.List;

//Classe herda o Standard Error mais é utilizada para apresentar a lista de validacoes (Validation Bean) de um campo especifico que apresentou um erro na validacao. 
public class ValidationError extends StandardError {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();
	
	public ValidationError(Integer status, String msg, Long timestamp) {
		super(status, msg, timestamp);
		// TODO Auto-generated constructor stub
	}

	public List<FieldMessage> getErrors() {
		return errors;
	}

	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}
	
	

	
	
}
