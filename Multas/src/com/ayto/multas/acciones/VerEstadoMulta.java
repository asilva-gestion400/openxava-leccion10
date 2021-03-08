package com.ayto.multas.acciones;

import org.openxava.actions.*;

import com.ayto.multas.modelo.*;

public class VerEstadoMulta extends ViewBaseAction {

	@Override
	public void execute() throws Exception {

		EstadoMulta estado = (EstadoMulta) getView().getValue("estado");
		
		addMessage("VerEstadoMulta.informacion", estado);	// i18n
	}
}
