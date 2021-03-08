package com.ayto.multas.modelo;

import javax.persistence.*;

@Entity
@Table(schema = ConstantesBD.ESQUEMA_MULTAS, name = "MULTPN")
public class TipoNif {
	
	@Id
	@Column(name = "TPNTIP",length = 10)
	private String tipo;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
