package com.ayto.multas.acciones;

import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.util.*;

import com.ayto.multas.modelo.*;

public class AlCambiarCodigoPostal extends OnChangePropertyBaseAction {

	@Override
	public void execute() throws Exception {
		
		Integer codigo = (Integer) getNewValue();
		
		if(!Is.empty(codigo)) {
			
			CodigoPostal codigoPostal = XPersistence.getManager().find(CodigoPostal.class, codigo);
			
			if(!Is.empty(codigoPostal)) {
			
				getView().setValue("provincia", codigoPostal.getProvincia());
				
				getView().setValue("municipio", codigoPostal.getMunicipio());
			}
			
			getView().setHidden("provincia", false);
			
			getView().setHidden("municipio", false);
			
		} else {
			
			getView().setHidden("provincia", true);
			
			getView().setHidden("municipio", true);
		}
	}
}
