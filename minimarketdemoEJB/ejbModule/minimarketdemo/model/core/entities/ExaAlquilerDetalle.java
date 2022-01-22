package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the exa_alquiler_detalle database table.
 * 
 */
@Entity
@Table(name="exa_alquiler_detalle")
@NamedQuery(name="ExaAlquilerDetalle.findAll", query="SELECT e FROM ExaAlquilerDetalle e")
public class ExaAlquilerDetalle implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_exa_alquiler_detalle", unique=true, nullable=false)
	private Integer idExaAlquilerDetalle;

	//bi-directional many-to-one association to ExaAlquiler
	@ManyToOne
	@JoinColumn(name="id_exa_alquiler_exa_alquiler")
	private ExaAlquiler exaAlquiler;

	//bi-directional many-to-one association to ExaProducto
	@ManyToOne
	@JoinColumn(name="id_exa_producto_exa_producto")
	private ExaProducto exaProducto;

	public ExaAlquilerDetalle() {
	}

	public Integer getIdExaAlquilerDetalle() {
		return this.idExaAlquilerDetalle;
	}

	public void setIdExaAlquilerDetalle(Integer idExaAlquilerDetalle) {
		this.idExaAlquilerDetalle = idExaAlquilerDetalle;
	}

	public ExaAlquiler getExaAlquiler() {
		return this.exaAlquiler;
	}

	public void setExaAlquiler(ExaAlquiler exaAlquiler) {
		this.exaAlquiler = exaAlquiler;
	}

	public ExaProducto getExaProducto() {
		return this.exaProducto;
	}

	public void setExaProducto(ExaProducto exaProducto) {
		this.exaProducto = exaProducto;
	}

}