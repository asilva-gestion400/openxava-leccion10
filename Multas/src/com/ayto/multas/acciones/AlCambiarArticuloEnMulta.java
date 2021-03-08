package com.ayto.multas.acciones;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import com.ayto.multas.modelo.*;

/** 
 * Acción que se ejecutará al cambiar el valor de la referencia articulo en Multa
 */
public class AlCambiarArticuloEnMulta extends OnChangePropertyBaseAction{

	@Override
	public void execute() throws Exception {

		String idArticulo = (String) getNewValue();
		
		if(!Is.empty(idArticulo)) {
			
			Articulo articulo = XPersistence.getManager().find(Articulo.class, idArticulo);
			
			if (articulo != null) {
				
				getView().setValue("importe", articulo.getImporte());
			}
		}
	}
}
