package com.ayto.multas.convertidores;

import javax.persistence.*;

import org.openxava.util.*;

public class ConverterCharDB400 implements AttributeConverter<String, String> {

	@Override
	public String convertToEntityAttribute(String cadena) {		
		
		if(Is.empty(cadena)) return null;
		
		return cadena.trim();
	}
	
	@Override
	public String convertToDatabaseColumn(String cadena) {
		
		if(Is.empty(cadena)) return "";

		return cadena;
	}


}
