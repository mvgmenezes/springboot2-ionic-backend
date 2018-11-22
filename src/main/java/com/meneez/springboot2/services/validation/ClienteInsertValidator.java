package com.meneez.springboot2.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.meneez.springboot2.domain.Cliente;
import com.meneez.springboot2.domain.enums.TipoCliente;
import com.meneez.springboot2.dto.ClienteNewDTO;
import com.meneez.springboot2.repositories.ClienteRepository;
import com.meneez.springboot2.resources.exception.FieldMessage;
import com.meneez.springboot2.services.validation.utils.BR;



public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {//ConstraintValidator<Nome da anotacao,Objeto que recebe a anotacao> 
	
	
	//criado para verificar se o email ja existe
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		//lista de field message(objeto criado por mim) para mostrar uma lista de erros
		List<FieldMessage> list = new ArrayList<>();
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCpf(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}
		
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCnpj(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux!= null) {
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