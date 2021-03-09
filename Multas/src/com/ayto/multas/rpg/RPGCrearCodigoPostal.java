package com.ayto.multas.rpg;

import java.math.*;
import java.util.*;

import com.ayto.multas.as400.*;
import com.ayto.multas.modelo.*;

public class RPGCrearCodigoPostal {

	private static final int SEGUNDOS_TIMEOUT = 10;

	private static final String BIBLOTECA = "G400ELCHE";
	
	private static final String RUTA_PROGRAMA = "/QSYS.LIB/G400ELCHE.LIB/GNJAVARPG.PGM";
	
	private static final int NUMERO_PARAMETROS = 4;
	
	private static final int POSICION_CODIGO = 0;
	
	private static final int LONGITUD_CODIGO = 5;
	
	private static final int DIGIT_CODIGO = 0;
	
	private static final int POSICION_PROVINCIA = 1;
	
	private static final int LONGITUD_PROVINCIA = 20;
	
	private static final int POSICION_MUNICIPIO = 2;
	
	private static final int LONGITUD_MUNICIPIO = 30;
	
	private static final int POSICION_ERROR = 3;
	
	private static final int LONGITUD_ERROR = 1;
	
	private static final int DIGIT_ERROR = 0;
	
	private static final int LONGITUD_TOTAL = LONGITUD_CODIGO + LONGITUD_PROVINCIA + LONGITUD_MUNICIPIO + LONGITUD_ERROR;

	public static boolean crear(CodigoPostal codigoPostal)  {
		
		boolean error = true;
		
		try {
		
			List<String> librerias = new ArrayList<>();
			
			librerias.add(BIBLOTECA);
			
			ConfiguracionAS400 configuracion = new ConfiguracionAS400();
			
			ProgramaAS400 programa = new ProgramaAS400(configuracion,RUTA_PROGRAMA, NUMERO_PARAMETROS, LONGITUD_TOTAL,librerias);

			programa.setParametrosPacked(POSICION_CODIGO, codigoPostal.getCodigo(), LONGITUD_CODIGO, DIGIT_CODIGO);		
			
			programa.setParametrosString(POSICION_PROVINCIA, codigoPostal.getProvincia(), LONGITUD_PROVINCIA);
			
			programa.setParametrosString(POSICION_MUNICIPIO, codigoPostal.getMunicipio(), LONGITUD_MUNICIPIO);
			
			programa.setParametrosPacked(POSICION_ERROR, 0, LONGITUD_ERROR, DIGIT_ERROR);

			programa.ejecutar(configuracion,SEGUNDOS_TIMEOUT);
			
			BigDecimal parametroError = programa.getParametrosPacked(POSICION_ERROR, LONGITUD_ERROR, DIGIT_ERROR);
			
			error = BigDecimal.ZERO.equals(parametroError);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
		}
		
		return error;
	}
}
