package com.meneez.springboot2.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.meneez.springboot2.services.exceptions.DataIntegrityException;
import com.meneez.springboot2.services.exceptions.ObjectNotFoundException;
/**
 * Classe auxiliar responsavel por interceptar uma exception ocorrida nas chamadas do rest (resources)
 * @author mvgmenezes
 *
 */
@ControllerAdvice
public class ResourceExceptionHandler {
	
	//Excecao personalizada
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		
		StandardError standardError = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(standardError);
	}

	//Excecao personalizada
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrity(DataIntegrityException e, HttpServletRequest request){
		
		StandardError standardError = new StandardError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(standardError);
	}

	
}
