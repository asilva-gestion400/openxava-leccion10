package com.ayto.multas.util;

import java.util.*;

import javax.persistence.*;

import org.openxava.util.*;


public class ConverterDateDB400 implements AttributeConverter<Date, Date> {

	@Override
	public Date convertToDatabaseColumn(Date fecha) {
		
		if (Is.empty(fecha)) return Dates.create(1, 1, 1);

		return fecha;
	}

	@Override
	public Date convertToEntityAttribute(Date fecha) {
		
		if (Is.empty(fecha)) return Dates.create(1, 1, 1);
		
		return fecha;
	}
}
