package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the salidas database table.
 * 
 */
@Entity
@Table(name="salidas")
@NamedQuery(name="Salida.findAll", query="SELECT s FROM Salida s")
public class Salida implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_salida", unique=true, nullable=false)
	private Integer idSalida;

	@Column(name="cantidad_salida", nullable=false, precision=8)
	private BigDecimal cantidadSalida;

	@Column(name="estado_salida", nullable=false)
	private Boolean estadoSalida;

	@Column(name="precio_venta", nullable=false, precision=8)
	private BigDecimal precioVenta;

	//bi-directional many-to-one association to Cabecera
	@ManyToOne
	@JoinColumn(name="id_cabecera")
	private Cabecera cabecera;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_persona")
	private Persona persona;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	public Salida() {
	}

	public Integer getIdSalida() {
		return this.idSalida;
	}

	public void setIdSalida(Integer idSalida) {
		this.idSalida = idSalida;
	}

	public BigDecimal getCantidadSalida() {
		return this.cantidadSalida;
	}

	public void setCantidadSalida(BigDecimal cantidadSalida) {
		this.cantidadSalida = cantidadSalida;
	}

	public Boolean getEstadoSalida() {
		return this.estadoSalida;
	}

	public void setEstadoSalida(Boolean estadoSalida) {
		this.estadoSalida = estadoSalida;
	}

	public BigDecimal getPrecioVenta() {
		return this.precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public Cabecera getCabecera() {
		return this.cabecera;
	}

	public void setCabecera(Cabecera cabecera) {
		this.cabecera = cabecera;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

}