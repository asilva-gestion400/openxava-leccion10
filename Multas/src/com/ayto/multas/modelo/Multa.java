package com.ayto.multas.modelo;

import java.math.*;
import java.util.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.*;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.model.*;
import org.openxava.util.*;

import com.ayto.multas.acciones.*;
import com.ayto.multas.calculadores.*;
import com.ayto.multas.convertidores.*;

import jdk.internal.agent.resources.*;

@View(members = 
	" anyo, fecha;" +
	" estado, articulo; " +
	" importe, porcentajeDescuento, importeConDescuento;" +
	" agente; " +
	" infractor; " +
	" vehiculoImplicado;segundoVehiculoImplicado;" +
	" observaciones")

@View(name="desdeInfractor" ,members = "anyo,fecha;estado;importe;agente")

@Tab(properties = "estado,anyo,fecha,importe, agente.codigo, agente.nombre, infractor.nif, infractor.nombre",
	 defaultOrder = "${fecha} DESC")

@Tab(name = "multasPendientes", 
	 properties = "estado, anyo,fecha,importe, agente.codigo, agente.nombre, infractor.nif, infractor.nombre",
	 defaultOrder = "${fecha} DESC",
	 baseCondition = "${estado} = 'PENDIENTE'")

@Tab(name= "multasPagadas", 
	 properties = "estado, anyo,fecha,importe, agente.codigo, agente.nombre, infractor.nif, infractor.nombre",
	 defaultOrder = "${fecha} DESC",
	 baseCondition = "${estado} = 'PAGADA'")
		
@Entity
@Table(schema = ConstantesBD.ESQUEMA_MULTAS, name = "MULMUL")
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "MULOID", length = 32)),
})
public class Multa extends Identifiable {
	
	@PrePersist @PreUpdate
	private void onPrePersistOrPreUpdate() {
		
		if(!Is.empty(agente)) {
			
			codigoAgente = agente.getCodigo();
		} 
		
		nifInfrantor = "";

		if(!Is.empty(infractor)) {
			
			nifInfrantor = infractor.getNif();
		}
		
		matriculaVehiculo1 = "";
		
		if(!Is.empty(vehiculoImplicado)) {
			
			matriculaVehiculo1 = vehiculoImplicado.getMatricula();
		}
		
		matriculaVehiculo2 = "";
		
		if(!Is.empty(segundoVehiculoImplicado)) {
			
			matriculaVehiculo2 = segundoVehiculoImplicado.getMatricula();
		}
	}
	
	 /* BeanValidator: si no es true devolverá error */
	@AssertTrue(
		message="importe_multa_incorrecto"	// i18n
	)
    private boolean isDatosErroneos() {
    	return !(getImporte() == null || getImporte().compareTo(BigDecimal.ZERO) <= 0);
    }
	
	@DefaultValueCalculator(value = CurrentYearCalculator.class)
	@Column(name = "MULANO", length = 4)
	private int anyo;
	
	@DefaultValueCalculator(value = CurrentDateCalculator.class)
	@Column(name = "MULFCH")
	@Convert(converter = ConverterDateDB400.class)
	private Date fecha;
	
	@Stereotype("DINERO")
	@Column(name = "MULIMP", length = 12, scale = 2)
	@Convert(converter = ConverterBigDecimalDB400.class)
	private BigDecimal importe;
	
	@Stereotype("TEXTO_GRANDE")
	@Column(name = "MULOBS",length = 100)
	@Convert(converter = ConverterCharDB400.class)
	private String observaciones;
	
	@Column(name = "MULAGT",length = 9)
	private int codigoAgente;
	
	@NoFrame
	@ReferenceView(value = "desdeMulta")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MULAGT", referencedColumnName = "AGTCOD", insertable = false, updatable = false)
	private Agente agente;
	
	@Column(name = "MULINF",length = 9)
	private String nifInfrantor;
	
	@NoFrame
	@ReferenceView(value = "desdeMulta")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MULINF", referencedColumnName = "INFNIF",insertable = false, updatable = false)
	private Infractor infractor;
	
	@DefaultValueCalculator(DefaultEstadoMultaCalculator.class)
	@OnChange(AlCambiarEstadoMulta.class)
	@Enumerated(EnumType.STRING)
	@Column(name = "MULEST",length = 50)
	private EstadoMulta estado;
	
	@Column(name = "MULVH1",length = 10)
	private String matriculaVehiculo1;
	
	@NoCreate @NoModify
	@ReferenceView(value="VehiculoSimple")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MULVH1", referencedColumnName = "VHCMTC",insertable = false, updatable = false)
	private Vehiculo vehiculoImplicado;
	
	@Column(name = "MULVH2",length = 10)
	private String matriculaVehiculo2;
	
	@ManyToOne @NoCreate @NoModify
	@ReferenceView(value="VehiculoSimple")
	@JoinColumn(name = "MULVH2", referencedColumnName = "VHCMTC",insertable = false, updatable = false)
	private Vehiculo segundoVehiculoImplicado;
	
	@Column(name = "MULDSC",length = 3, scale = 2)
	@Convert(converter = ConverterBigDecimalDB400.class)
	private BigDecimal porcentajeDescuento;
	
	@ManyToOne 
	@DescriptionsList(descriptionProperties = "informacion")
	@OnChange(AlCambiarArticuloEnMulta.class)
	@JoinColumn(name = "MULART", referencedColumnName = "ARTOID")
	private Articulo articulo;
	
	/* propiedad calculada */
	@Stereotype("MONEY")
	@Depends("porcentajeDescuento, importe")
	public BigDecimal getImporteConDescuento() {
		if(importe == null || porcentajeDescuento == null) return BigDecimal.ZERO;
		return importe.subtract(importe.multiply(porcentajeDescuento));
	}
	
	public int getAnyo() {
		return anyo;
	}

	public void setAnyo(int anyo) {
		this.anyo = anyo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getImporte() {
		return importe == null ? BigDecimal.ZERO : importe;
//		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public Agente getAgente() {
		return agente;
	}

	public void setAgente(Agente agente) {
		this.agente = agente;
	}

	public Infractor getInfractor() {
		return infractor;
	}

	public void setInfractor(Infractor infractor) {
		this.infractor = infractor;
	}

	public EstadoMulta getEstado() {
		return estado;
	}

	public void setEstado(EstadoMulta estado) {
		this.estado = estado;
	}

	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public Vehiculo getVehiculoImplicado() {
		return vehiculoImplicado;
	}

	public void setVehiculoImplicado(Vehiculo vehiculoImplicado) {
		this.vehiculoImplicado = vehiculoImplicado;
	}

	public Vehiculo getSegundoVehiculoImplicado() {
		return segundoVehiculoImplicado;
	}

	public void setSegundoVehiculoImplicado(Vehiculo segundoVehiculoImplicado) {
		this.segundoVehiculoImplicado = segundoVehiculoImplicado;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
}
