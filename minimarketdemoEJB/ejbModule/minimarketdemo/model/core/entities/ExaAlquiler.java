package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the exa_alquiler database table.
 * 
 */
@Entity
@Table(name="exa_alquiler")
@NamedQuery(name="ExaAlquiler.findAll", query="SELECT e FROM ExaAlquiler e")
public class ExaAlquiler implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_exa_alquiler", unique=true, nullable=false)
	private Integer idExaAlquiler;

	@Column(nullable=false, length=20)
	private String apellidos;

	@Column(nullable=false)
	private Boolean aprobado;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fecha;

	@Column(nullable=false, length=20)
	private String nombres;

	@Column(nullable=false, length=40)
	private String observaciones;

	@Column(nullable=false, precision=7, scale=2)
	private BigDecimal total;

	//bi-directional many-to-one association to ExaAlquilerDetalle
	@OneToMany(mappedBy="exaAlquiler")
	private List<ExaAlquilerDetalle> exaAlquilerDetalles;

	public ExaAlquiler() {
	}

	public Integer getIdExaAlquiler() {
		return this.idExaAlquiler;
	}

	public void setIdExaAlquiler(Integer idExaAlquiler) {
		this.idExaAlquiler = idExaAlquiler;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Boolean getAprobado() {
		return this.aprobado;
	}

	public void setAprobado(Boolean aprobado) {
		this.aprobado = aprobado;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNombres() {
		return this.nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getObservaciones() {
		return this.observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public List<ExaAlquilerDetalle> getExaAlquilerDetalles() {
		return this.exaAlquilerDetalles;
	}

	public void setExaAlquilerDetalles(List<ExaAlquilerDetalle> exaAlquilerDetalles) {
		this.exaAlquilerDetalles = exaAlquilerDetalles;
	}

	public ExaAlquilerDetalle addExaAlquilerDetalle(ExaAlquilerDetalle exaAlquilerDetalle) {
		getExaAlquilerDetalles().add(exaAlquilerDetalle);
		exaAlquilerDetalle.setExaAlquiler(this);

		return exaAlquilerDetalle;
	}

	public ExaAlquilerDetalle removeExaAlquilerDetalle(ExaAlquilerDetalle exaAlquilerDetalle) {
		getExaAlquilerDetalles().remove(exaAlquilerDetalle);
		exaAlquilerDetalle.setExaAlquiler(null);

		return exaAlquilerDetalle;
	}

}