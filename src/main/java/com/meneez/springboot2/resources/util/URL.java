package com.meneez.springboot2.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

	//recebe uma string vinda da URL com numeros e virgulas("1,2,3") e retorna uma lista de integer
	public static List<Integer> decodeIntList(String s){
		String[] vet = s.split(",");
		
		List<Integer> list = new ArrayList<>();
		for (int i=0; i<vet.length; i++) {
			list.add(Integer.parseInt(vet[i]));
			
		}
		
		return list;
		
		//Usando Lambda pode substituir o metodo inteiro por essa instrucao
		//return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	
	//metodo para pegar uma string com Encode e decodificar. 
	public static String decodeParam(String s) {
		
		try {
			return URLDecoder.decode(s, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
		
		
		
	}
}
