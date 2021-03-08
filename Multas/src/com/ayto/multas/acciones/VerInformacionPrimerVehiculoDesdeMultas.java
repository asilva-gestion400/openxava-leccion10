package com.ayto.multas.acciones;

import org.openxava.actions.*;

public class VerInformacionPrimerVehiculoDesdeMultas extends ViewBaseAction {

	@Override
	public void execute() throws Exception {
		
		String marca = getView().getValueString("vehiculoImplicado.marca");
		String modelo = getView().getValueString("vehiculoImplicado.modelo");
		
		addMessage("VerInformacionVehiculo.informacion", "'" + marca + "'", modelo);	// i18n
	}
}
