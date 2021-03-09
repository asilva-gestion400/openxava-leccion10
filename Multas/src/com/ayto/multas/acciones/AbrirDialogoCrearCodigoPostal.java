package com.ayto.multas.acciones;

import org.openxava.actions.*;
import org.openxava.util.*;

import com.ayto.multas.modelo.*;

public class AbrirDialogoCrearCodigoPostal extends ViewBaseAction {

	@Override
	public void execute() throws Exception {

		showDialog();
		
		getView().setTitle(Labels.get("DialogoCodigoPostal.title"));

		getView().setModelName(DialogoCodigoPostal.class.getSimpleName());
		
		setControllers("DialogoCodigoPostal");
	}
}
