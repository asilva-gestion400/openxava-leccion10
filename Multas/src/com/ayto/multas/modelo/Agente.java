package com.ayto.multas.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.ayto.multas.convertidores.*;

@Tab(properties = "codigo,nombre,direccion.viaPublica,direccion.codigoPostal", 
	 defaultOrder =  "${codigo} DESC")

@Views({
	@View(members = "datosGenerales[codigo, nombre]; direccion{direccion},multas{multas}"),
	@View(name = "desdeMulta", members = "codigo, nombre")
})
@Entity
@Table(schema = ConstantesBD.ESQUEMA_MULTAS, name = "MULAGT")
public class Agente {
	
	@Id
	@Column(name = "AGTCOD",length = 9)
	private int codigo;
	    
	@Column(name = "AGTNOM",length = 40)
	@Convert(converter = ConverterCharDB400.class)
	private String nombre;
	
	@Embedded @NoFrame
	@AttributeOverrides({
		@AttributeOverride(name = "viaPublica", column = @Column(name = "AGTVPC", length = 30)),
		@AttributeOverride(name = "codigoPostal", column = @Column(name = "AGTCGP", length = 5)),
		@AttributeOverride(name = "municipio", column = @Column(name = "AGTMUN", length = 20)),
		@AttributeOverride(name = "provincia", column = @Column(name = "AGTPRV", length = 30))
	})
	private Direccion direccion;
	
	@ReadOnly
	@OneToMany(mappedBy = "agente")
	@ListProperties(value = "anyo, fecha, importe, infractor.nif, infractor.nombre")
	private Collection<Multa> multas;

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Direccion getDireccion() {
		return direccion;
	}

	public void setDireccion(Direccion direccion) {
		this.direccion = direccion;
	}

	public Collection<Multa> getMultas() {
		return multas;
	}

	public void setMultas(Collection<Multa> multas) {
		this.multas = multas;
	}
}
