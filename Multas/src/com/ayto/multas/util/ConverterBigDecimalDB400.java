package com.ayto.multas.util;

import java.math.BigDecimal;

import javax.persistence.AttributeConverter;

import org.openxava.util.Is;

public class ConverterBigDecimalDB400 implements AttributeConverter<BigDecimal, BigDecimal> {

	@Override
	public BigDecimal convertToDatabaseColumn(BigDecimal bigDecimal) {

		if (Is.empty(bigDecimal)) return new BigDecimal("0.00");
		
		return bigDecimal;
	}

	@Override
	public BigDecimal convertToEntityAttribute(BigDecimal bigDecimal) {
		
		if (Is.empty(bigDecimal)) return new BigDecimal("0.00");
		
		return bigDecimal;
	}

}
