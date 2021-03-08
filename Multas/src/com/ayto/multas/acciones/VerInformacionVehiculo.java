package com.ayto.multas.acciones;

import org.openxava.actions.*;

public class VerInformacionVehiculo extends ViewBaseAction {

	@Override
	public void execute() throws Exception {
		String marca = getView().getValueString("marca");
		String modelo = getView().getValueString("modelo");
		
		addMessage("VerInformacionVehiculo.informacion", "'" + marca + "'", modelo);	// i18n
	}
}
