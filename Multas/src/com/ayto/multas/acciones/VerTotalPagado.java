package com.ayto.multas.acciones;

import java.math.*;
import java.util.*;

import org.openxava.actions.*;
import org.openxava.model.*;

import com.ayto.multas.modelo.*;

public class VerTotalPagado extends TabBaseAction{

	@Override
	public void execute() throws Exception {

		BigDecimal total = BigDecimal.ZERO;

		for (Map key: getTab().getSelectedKeys()) {
			
			Multa multa = (Multa)MapFacade.findEntity(getModelName(), key);
			
			total = total.add(multa.getImporte());
		}
		
		addInfo("VerTotalPagado.total", total);	// i18n
	}
}
