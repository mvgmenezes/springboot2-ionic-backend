package com.meneez.springboot2.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.meneez.springboot2.domain.Cliente;
import com.meneez.springboot2.dto.ClienteDTO;
import com.meneez.springboot2.repositories.ClienteRepository;
import com.meneez.springboot2.resources.exception.FieldMessage;



public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {//ConstraintValidator<Nome da anotacao,Objeto que recebe a anotacao> 
	
	
	//criado para verificar se o email do cliente a ser atualizado ja existe em outro cliente
	//pegando do request o id enviado via request
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		//pegando o id do request enviado
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));
		
		//lista de field message(objeto criado por mim) para mostrar uma lista de erros
		List<FieldMessage> list = new ArrayList<>();
		
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		//verifico se o email existe e se o email enviado ja existe em outro usuario do banco de dados
		if (aux!= null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		//o framework de validacao nao trabalha com o campo FieldMessage pois foi um objeto criado, com esse for
		//é feita a transferencia dos erros encontrados para a lista de erros da framework.
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}