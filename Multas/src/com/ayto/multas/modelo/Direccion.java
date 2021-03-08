package com.ayto.multas.modelo;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.ayto.multas.acciones.*;
import com.ayto.multas.convertidores.*;

@View(name = "Municipio", members = "municipio")

@Embeddable
public class Direccion {

	@Column(length = 30)
	@Convert(converter = ConverterCharDB400.class)
    private String viaPublica;
 
    @Column(length = 5)
    @OnChange(value = AlCambiarCodigoPostal.class)
    private int codigoPostal;
 
    @Column(length = 20)
    @Convert(converter = ConverterCharDB400.class)
    private String municipio;
 
    @Column(length = 30)
    @Convert(converter = ConverterCharDB400.class)
    private String provincia;

	public String getViaPublica() {
		return viaPublica;
	}

	public void setViaPublica(String viaPublica) {
		this.viaPublica = viaPublica;
	}

	public int getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
}
