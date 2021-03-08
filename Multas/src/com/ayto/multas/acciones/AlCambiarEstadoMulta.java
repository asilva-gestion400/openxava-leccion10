package com.ayto.multas.acciones;

import org.openxava.actions.*;

import com.ayto.multas.modelo.*;


/**
 * Acción a ejecutar cuando cambie la referencia cliente en el módulo Factura
 */
public class AlCambiarEstadoMulta extends OnChangePropertyBaseAction {

	@Override
	public void execute() throws Exception {
		
		EstadoMulta estado = (EstadoMulta) getNewValue();
		
		addWarning("AlCambiarEstado.warning",  estado);
	}
}
