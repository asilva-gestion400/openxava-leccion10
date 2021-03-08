package com.ayto.multas.validadores;

import org.openxava.util.*;
import org.openxava.validators.*;

import com.ayto.multas.modelo.*;

public class ValidadorBorrarVehiculo implements IRemoveValidator{

	private Vehiculo vehiculo;
	
	@Override
	public void validate(Messages errors) throws Exception {
		if (!vehiculo.getMultas().isEmpty()) {
			errors.add("vehiculo_con_multas_asociadas", "'" + vehiculo.getMatricula() + "'"); // i18n: los parámetros entre comillas simples (') no serán traducidos
		}
	}

	@Override
	public void setEntity(Object entity) throws Exception {
		vehiculo = (Vehiculo) entity;
	}

}
