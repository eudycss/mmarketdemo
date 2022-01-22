package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the alq_productos database table.
 * 
 */
@Entity
@Table(name="alq_productos")
@NamedQuery(name="AlqProducto.findAll", query="SELECT a FROM AlqProducto a")
public class AlqProducto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_alq_producto", unique=true, nullable=false)
	private Integer idAlqProducto;

	@Column(precision=7, scale=2)
	private BigDecimal costo;

	@Column(length=50)
	private String descripcion;

	public AlqProducto() {
	}

	public Integer getIdAlqProducto() {
		return this.idAlqProducto;
	}

	public void setIdAlqProducto(Integer idAlqProducto) {
		this.idAlqProducto = idAlqProducto;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}