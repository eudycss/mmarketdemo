package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the exa_producto database table.
 * 
 */
@Entity
@Table(name="exa_producto")
@NamedQuery(name="ExaProducto.findAll", query="SELECT e FROM ExaProducto e")
public class ExaProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_exa_producto", unique=true, nullable=false)
	private Integer idExaProducto;

	@Column(nullable=false, precision=7, scale=2)
	private BigDecimal costo;

	@Column(nullable=false, length=40)
	private String nombre;

	//bi-directional many-to-one association to ExaAlquilerDetalle
	@OneToMany(mappedBy="exaProducto")
	private List<ExaAlquilerDetalle> exaAlquilerDetalles;

	public ExaProducto() {
	}

	public Integer getIdExaProducto() {
		return this.idExaProducto;
	}

	public void setIdExaProducto(Integer idExaProducto) {
		this.idExaProducto = idExaProducto;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<ExaAlquilerDetalle> getExaAlquilerDetalles() {
		return this.exaAlquilerDetalles;
	}

	public void setExaAlquilerDetalles(List<ExaAlquilerDetalle> exaAlquilerDetalles) {
		this.exaAlquilerDetalles = exaAlquilerDetalles;
	}

	public ExaAlquilerDetalle addExaAlquilerDetalle(ExaAlquilerDetalle exaAlquilerDetalle) {
		getExaAlquilerDetalles().add(exaAlquilerDetalle);
		exaAlquilerDetalle.setExaProducto(this);

		return exaAlquilerDetalle;
	}

	public ExaAlquilerDetalle removeExaAlquilerDetalle(ExaAlquilerDetalle exaAlquilerDetalle) {
		getExaAlquilerDetalles().remove(exaAlquilerDetalle);
		exaAlquilerDetalle.setExaProducto(null);

		return exaAlquilerDetalle;
	}

}