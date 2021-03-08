package com.ayto.multas.acciones;

import org.openxava.actions.*;

import com.ayto.multas.modelo.*;


/**
 * Acci�n a ejecutar cuando cambie la referencia cliente en el m�dulo Factura
 */
public class AlCambiarEstadoMulta extends OnChangePropertyBaseAction {

	@Override
	public void execute() throws Exception {
		
		EstadoMulta estado = (EstadoMulta) getNewValue();
		
		addWarning("AlCambiarEstado.warning",  estado);
	}
}
