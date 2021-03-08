package com.ayto.multas.calculadores;

import org.openxava.calculators.*;

import com.ayto.multas.modelo.*;

public class DefaultEstadoMultaCalculator implements ICalculator {

	@Override
	public Object calculate() throws Exception {
		return EstadoMulta.PENDIENTE;
	}
}
