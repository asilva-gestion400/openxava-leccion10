package com.ayto.multas.acciones;

import org.apache.commons.beanutils.*;
import org.openxava.actions.*;
import org.openxava.jpa.*;
import org.openxava.model.*;

import com.ayto.multas.modelo.*;

public class ClonarMulta extends TabBaseAction {

	@Override
	public void execute() throws Exception {
		
		if(getSelectedKeys().length == 0) {
			
			addError("err.multaNoSeleccionada");
			
		} else if(getSelectedKeys().length > 1) {
			
			addError("err.multaMasDeUna");
			
		} else {
			
			Multa multa = (Multa) MapFacade.findEntity("Multa", getSelectedKeys()[0]);
			
			Multa multaClonada = (Multa) BeanUtils.cloneBean(multa);
			
			multaClonada.setId(null);
			
			multaClonada.setObservaciones("**** MULTA CLONADA ****");
			
			XPersistence.getManager().persist(multaClonada);
			
			setNextMode(DETAIL);
			
			getView().setValue("id", multaClonada.getId());
			
			getView().findObject();
		}
	}
}
