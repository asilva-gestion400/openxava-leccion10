package com.ayto.multas.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;

import com.ayto.multas.util.*;


@Tab(properties = "nif,nombre,direccion.viaPublica,direccion.codigoPostal, fechaCaducidadPermisoConducir", 
	 defaultOrder =  "${fechaCaducidadPermisoConducir}")

@View(members = "tipoNif,nif,nombre;" + 
				"telefono;" +
				"fechaCaducidadPermisoConducir;" +
				"direccion{direccion}, multas{multas}")

@View(name = "desdeMulta", members = "nif, nombre")
@Entity
@Table(schema = ConstantesBD.ESQUEMA_MULTAS, name = "MULINF")
public class Infractor {
	
	@Id
	@Column(name = "INFNIF", length = 9)
	@Convert(converter = ConverterCharDB400.class)
	private String nif;
	
	@NoCreate @NoModify
	@ManyToOne(fetch = FetchType.LAZY)
	@DescriptionsList(descriptionProperties = "tipo")
	@JoinColumn(name = "INFTPN", referencedColumnName = "TPNTIP")
	private TipoNif tipoNif;
	
	@Column(name = "INFNOM",length = 40)
	@Convert(converter = ConverterCharDB400.class)
	private String nombre;
	
	@Column(name = "INFTLF",length = 20)
	@Convert(converter = ConverterCharDB400.class)
	private String telefono;
	
	@NoFrame
	@Embedded
	@AttributeOverrides({
		@AttributeOverride(name = "viaPublica", column = @Column(name = "INFVPC", length = 30)),
		@AttributeOverride(name = "codigoPostal", column = @Column(name = "INFCGP", length = 5)),
		@AttributeOverride(name = "municipio", column = @Column(name = "INFMUN", length = 20)),
		@AttributeOverride(name = "provincia", column = @Column(name = "INFPRV", length = 30))
	})
	private Direccion direccion;
	
	@Column(name = "INFFCP")
	@Convert(converter = ConverterDateDB400.class)
	private Date fechaCaducidadPermisoConducir;
	
	@ReadOnly
	@OneToMany(fetch = FetchType.LAZY ,mappedBy = "infractor")
	@ListProperties(value = "anyo, fecha, importe, agente.codigo, agente.nombre")
	@CollectionView("desdeInfractor")
	private Collection<Multa> multas;

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public TipoNif getTipoNif() {
		return tipoNif;
	}

	public void setTipoNif(TipoNif tipoNif) {
		this.tipoNif = tipoNif;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Date getFechaCaducidadPermisoConducir() {
		return fechaCaducidadPermisoConducir;
	}

	public void setFechaCaducidadPermisoConducir(Date fechaCaducidadPermisoConducir) {
		this.fechaCaducidadPermisoConducir = fechaCaducidadPermisoConducir;
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
