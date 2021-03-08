package com.ayto.multas.acciones;

import org.openxava.actions.*;

public class VerInformacionSegundoVehiculoDesdeMultas extends ViewBaseAction {

	@Override
	public void execute() throws Exception {
		
		String marca = getView().getValueString("segundoVehiculoImplicado.marca");
		String modelo = getView().getValueString("segundoVehiculoImplicado.modelo");
		
		addInfo("VerInformacionVehiculo.informacion", "'" + marca + "'", modelo);	// i18n
	}
}
