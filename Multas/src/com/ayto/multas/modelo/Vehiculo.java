package com.ayto.multas.modelo;

import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.util.*;

import com.ayto.multas.calculadores.*;
import com.ayto.multas.convertidores.*;
import com.ayto.multas.validadores.*;

@View(members="matricula, informacionVehiculo; marca, modelo; tipo; multas;multasComoSegundoVehiculo")
@View(name="VehiculoSimple", members="matricula; marca, modelo;")
@View(name="VehiculoCompleta", extendsView = "VehiculoSimple", members="multas;")
@Entity
@Table(schema = ConstantesBD.ESQUEMA_MULTAS, name = "MULVHC")
@RemoveValidator(ValidadorBorrarVehiculo.class)
public class Vehiculo {
	
	@Id 
	@Column(length = 10, name = "VHCMTC")
	private String matricula;
	
	@Column(length = 50, name = "VHCMRC")
	@Convert(converter = ConverterCharDB400.class)
	private String marca;
	
	@Column(length = 50, name = "VHCMDL")
	@Convert(converter = ConverterCharDB400.class)
	private String modelo;

	@OneToMany(mappedBy = "vehiculoImplicado")
	@ListProperties("estado,anyo,fecha,importe")
	@ReadOnly 
	private Collection<Multa> multas;
	
	@OneToMany(mappedBy = "segundoVehiculoImplicado")
	@ListProperties("estado,anyo,fecha,importe")
	@ReadOnly 
	private Collection<Multa> multasComoSegundoVehiculo;
	
	@DefaultValueCalculator(DefaultTipoVehiculo.class)
	@Enumerated(EnumType.STRING)
	@Column(length = 50, name = "VHCTPV")
	private TipoVehiculo tipo;
	
	/* propiedad calculada */
	@Depends("marca,modelo,tipo")
	public String getInformacionVehiculo() {
		return (getTipo() != null ? Labels.get(getTipo().name()) : "") + " " + getMarca() + " " + getModelo();
	}
	
	/* */

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getMarca() {
		return marca == null ? "" : marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Collection<Multa> getMultas() {
		return multas;
	}

	public void setMultas(Collection<Multa> multas) {
		this.multas = multas;
	}

	public Collection<Multa> getMultasComoSegundoVehiculo() {
		return multasComoSegundoVehiculo;
	}

	public void setMultasComoSegundoVehiculo(Collection<Multa> multasComoSegundoVehiculo) {
		this.multasComoSegundoVehiculo = multasComoSegundoVehiculo;
	}

	public TipoVehiculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoVehiculo tipo) {
		this.tipo = tipo;
	}

}
