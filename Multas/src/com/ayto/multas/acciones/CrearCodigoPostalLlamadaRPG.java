package com.ayto.multas.acciones;

import org.openxava.actions.*;
import org.openxava.util.*;

import com.ayto.multas.modelo.*;
import com.ayto.multas.rpg.*;

public class CrearCodigoPostalLlamadaRPG extends ViewBaseAction {

	@Override
	public void execute() throws Exception {
		
		Integer codigo = (Integer) getView().getValue("codigo");
		
		String provincia = getView().getValueString("provincia");
		
		String municipio = getView().getValueString("municipio");
		
		if(!Is.empty(codigo) && !Is.empty(provincia) && !Is.empty(municipio)) {
			
			CodigoPostal codigoPostal = new CodigoPostal();
			
			codigoPostal.setCodigo(codigo);
			
			codigoPostal.setProvincia(provincia.toUpperCase());
			
			codigoPostal.setMunicipio(municipio.toUpperCase());
			
			boolean creado = RPGCrearCodigoPostal.crear(codigoPostal);
			
			if(creado) {
				
				closeDialog();
				
				addMessage("msg.codigoPostal_creado",codigo.toString(),provincia,municipio);

			} else {
			
				addError("err.codigoPostal_yaExiste",codigo.toString());
			}
			
		} else {
			
			addError("err.debesRellenarTodosLosCampos");
		}
	}
}
