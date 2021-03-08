package com.ayto.multas.modelo;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.ayto.multas.convertidores.*;

@Entity
@Table(schema = ConstantesBD.ESQUEMA_MULTAS, name = "MULCDP")
@View(members = "codigo;provincia,municipio")
public class CodigoPostal {

	@Id
	@Column(name = "CPCOD", length = 5)
	private int codigo;
	
	@Column(name = "CPPRV", length = 50)
	@Convert(converter = ConverterCharDB400.class)
	private String provincia;
	
	@Column(name = "CPMUN", length = 50)
	@Convert(converter = ConverterCharDB400.class)
	private String municipio;

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
}
