package com.ayto.multas.modelo;

import org.openxava.annotations.*;

@View(members = "codigo,provincia,municipio;observaciones")
public class DialogoCodigoPostal {

	private int codigo;
	
	private String provincia;
	
	private String municipio;
	
	@Stereotype("MEMO")
	private String observaciones;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}
