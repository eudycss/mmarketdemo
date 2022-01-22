package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the cabecera database table.
 * 
 */
@Entity
@Table(name="cabecera")
@NamedQuery(name="Cabecera.findAll", query="SELECT c FROM Cabecera c")
public class Cabecera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cabecera", unique=true, nullable=false)
	private Integer idCabecera;

	@Column(name="estado_cabecera", nullable=false)
	private Boolean estadoCabecera;

	@Temporal(TemporalType.DATE)
	@Column(name="fecha_transaccion", nullable=false)
	private Date fechaTransaccion;

	@Column(name="total_transaccion", nullable=false, precision=8)
	private BigDecimal totalTransaccion;

	//bi-directional many-to-one association to Ingreso
	@OneToMany(mappedBy="cabecera")
	private List<Ingreso> ingresos;

	//bi-directional many-to-one association to Salida
	@OneToMany(mappedBy="cabecera")
	private List<Salida> salidas;

	public Cabecera() {
	}

	public Integer getIdCabecera() {
		return this.idCabecera;
	}

	public void setIdCabecera(Integer idCabecera) {
		this.idCabecera = idCabecera;
	}

	public Boolean getEstadoCabecera() {
		return this.estadoCabecera;
	}

	public void setEstadoCabecera(Boolean estadoCabecera) {
		this.estadoCabecera = estadoCabecera;
	}

	public Date getFechaTransaccion() {
		return this.fechaTransaccion;
	}

	public void setFechaTransaccion(Date fechaTransaccion) {
		this.fechaTransaccion = fechaTransaccion;
	}

	public BigDecimal getTotalTransaccion() {
		return this.totalTransaccion;
	}

	public void setTotalTransaccion(BigDecimal totalTransaccion) {
		this.totalTransaccion = totalTransaccion;
	}

	public List<Ingreso> getIngresos() {
		return this.ingresos;
	}

	public void setIngresos(List<Ingreso> ingresos) {
		this.ingresos = ingresos;
	}

	public Ingreso addIngreso(Ingreso ingreso) {
		getIngresos().add(ingreso);
		ingreso.setCabecera(this);

		return ingreso;
	}

	public Ingreso removeIngreso(Ingreso ingreso) {
		getIngresos().remove(ingreso);
		ingreso.setCabecera(null);

		return ingreso;
	}

	public List<Salida> getSalidas() {
		return this.salidas;
	}

	public void setSalidas(List<Salida> salidas) {
		this.salidas = salidas;
	}

	public Salida addSalida(Salida salida) {
		getSalidas().add(salida);
		salida.setCabecera(this);

		return salida;
	}

	public Salida removeSalida(Salida salida) {
		getSalidas().remove(salida);
		salida.setCabecera(null);

		return salida;
	}

}