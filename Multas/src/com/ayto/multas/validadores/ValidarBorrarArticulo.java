package com.ayto.multas.validadores;

import org.openxava.util.*;
import org.openxava.validators.*;

import com.ayto.multas.modelo.*;

public class ValidarBorrarArticulo implements IRemoveValidator{

	private Articulo articulo;
	
	@Override
	public void validate(Messages errors) throws Exception {
		if (articulo.hayMultasAsociadas()) {
			errors.add("ValidarBorrarArticulo.multasAsociadas");	// i18n
		}
	}

	@Override
	public void setEntity(Object entity) throws Exception {
		articulo = (Articulo) entity;
	}

}
