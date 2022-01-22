package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ingresos database table.
 * 
 */
@Entity
@Table(name="ingresos")
@NamedQuery(name="Ingreso.findAll", query="SELECT i FROM Ingreso i")
public class Ingreso implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ingreso", unique=true, nullable=false)
	private Integer idIngreso;

	@Column(name="cantidad_ingresos", nullable=false, precision=8)
	private BigDecimal cantidadIngresos;

	@Column(name="estado_ingreso", nullable=false)
	private Boolean estadoIngreso;

	@Column(name="precio_compra", nullable=false, precision=8, scale=2)
	private BigDecimal precioCompra;

	//bi-directional many-to-one association to Cabecera
	@ManyToOne
	@JoinColumn(name="id_cabecera")
	private Cabecera cabecera;

	//bi-directional many-to-one association to Producto
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	//bi-directional many-to-one association to Proveedore
	@ManyToOne
	@JoinColumn(name="id_proveedor")
	private Proveedore proveedore;

	public Ingreso() {
	}

	public Integer getIdIngreso() {
		return this.idIngreso;
	}

	public void setIdIngreso(Integer idIngreso) {
		this.idIngreso = idIngreso;
	}

	public BigDecimal getCantidadIngresos() {
		return this.cantidadIngresos;
	}

	public void setCantidadIngresos(BigDecimal cantidadIngresos) {
		this.cantidadIngresos = cantidadIngresos;
	}

	public Boolean getEstadoIngreso() {
		return this.estadoIngreso;
	}

	public void setEstadoIngreso(Boolean estadoIngreso) {
		this.estadoIngreso = estadoIngreso;
	}

	public BigDecimal getPrecioCompra() {
		return this.precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public Cabecera getCabecera() {
		return this.cabecera;
	}

	public void setCabecera(Cabecera cabecera) {
		this.cabecera = cabecera;
	}

	public Producto getProducto() {
		return this.producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Proveedore getProveedore() {
		return this.proveedore;
	}

	public void setProveedore(Proveedore proveedore) {
		this.proveedore = proveedore;
	}

}