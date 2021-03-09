package com.ayto.multas.modelo;

import java.math.*;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.openxava.annotations.*;
import org.openxava.jpa.*;
import org.openxava.model.*;
import org.openxava.util.*;

import com.ayto.multas.util.*;
import com.ayto.multas.validadores.*;

@Entity
@Table(schema = ConstantesBD.ESQUEMA_MULTAS, name = "MULART")
@RemoveValidator(ValidarBorrarArticulo.class)
@AttributeOverrides({
	@AttributeOverride(name = "id", column = @Column(name = "ARTOID", length = 32)),
})
public class Articulo extends Identifiable {
	
	@AssertTrue(
		message = "descripcion_rellena_si_importe_mayor_cero"	// i18n
	)
	private boolean isValido() {
		if (getImporte() != null && getImporte().compareTo(BigDecimal.ZERO) > 0) {
			return !Is.empty(getDescripcion());
		}
		return true;
	}
	
	@Column(name = "ARTCOD", length = 3)
	private int articulo;
	
	@Column(name = "ARTAPT", length = 3)
	@Convert(converter = ConverterCharDB400.class)
	private String apartado;
	
	@Column(name = "ARTOPC", length = 3)
	@Convert(converter = ConverterCharDB400.class)
	private String opcion;
	
	@Column(name = "ARTRGM", length = 3)
	@Convert(converter = ConverterCharDB400.class)
	private String reglamento;
	
	@Stereotype("MONEY")
	@Column(name = "ARTIMP", length = 13, scale = 2)
	@Convert(converter = ConverterBigDecimalDB400.class)
	private BigDecimal importe;
	
	@Stereotype("MEMO")
	@Column(name = "ARTDES", length = 300)
	@Convert(converter = ConverterCharDB400.class)
	private String descripcion;
	
	@ReadOnly
	@ListProperties(value = "anyo, fecha, importe, infractor.nif, infractor.nombre")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "articulo")
	private Collection<Multa> multas;
	
	@Hidden
	public boolean hayMultasAsociadas(){
		if (Is.empty(getId())) return false;

		String sentencia = "from Multa where articulo.id = :idArticulo";
		Query query = XPersistence.getManager().createQuery(sentencia);
		query.setParameter("idArticulo", getId());
		return !query.getResultList().isEmpty();	
	}
	
	/* */
	
	public int getArticulo() {
		return articulo;
	}

	public void setArticulo(int articulo) {
		this.articulo = articulo;
	}

	public String getApartado() {
		return apartado;
	}

	public void setApartado(String apartado) {
		this.apartado = apartado;
	}

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public String getReglamento() {
		return reglamento;
	}

	public void setReglamento(String reglamento) {
		this.reglamento = reglamento;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Collection<Multa> getMultas() {
		return multas;
	}

	public void setMultas(Collection<Multa> multas) {
		this.multas = multas;
	}
}
