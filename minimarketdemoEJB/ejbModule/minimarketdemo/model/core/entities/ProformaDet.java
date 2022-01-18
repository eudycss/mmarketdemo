package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the proforma_det database table.
 * 
 */
@Entity
@Table(name="proforma_det")
@NamedQuery(name="ProformaDet.findAll", query="SELECT p FROM ProformaDet p")
public class ProformaDet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pf_det_id", unique=true, nullable=false)
	private Integer pfDetId;

	

	@Column(name="pf_det_cantidad", nullable=false)
	private Integer pfDetCantidad;

	@Column(name="pf_det_precio", nullable=false, precision=131089)
	private BigDecimal pfDetPrecio;

	@Column(name="pf_det_preciototal", nullable=false, precision=131089)
	private BigDecimal pfDetPreciototal;

	
	
	@ManyToOne
	@JoinColumn(name="id_producto")
	private Producto producto;

	//bi-directional many-to-one association to ProformasCab
	@ManyToOne
	@JoinColumn(name="pf_cab_id")
	private ProformasCab proformasCab;

	public ProformaDet() {
	}

	public Integer getPfDetId() {
		return this.pfDetId;
	}

	public void setPfDetId(Integer pfDetId) {
		this.pfDetId = pfDetId;
	}



	public Integer getPfDetCantidad() {
		return this.pfDetCantidad;
	}

	public void setPfDetCantidad(Integer pfDetCantidad) {
		this.pfDetCantidad = pfDetCantidad;
	}

	public BigDecimal getPfDetPrecio() {
		return this.pfDetPrecio;
	}

	public void setPfDetPrecio(BigDecimal pfDetPrecio) {
		this.pfDetPrecio = pfDetPrecio;
	}

	public BigDecimal getPfDetPreciototal() {
		return this.pfDetPreciototal;
	}

	public void setPfDetPreciototal(BigDecimal pfDetPreciototal) {
		this.pfDetPreciototal = pfDetPreciototal;
	}

	public ProformasCab getProformasCabs() {
		return this.proformasCab;
	}

	public void setProformasCabs(ProformasCab proformasCabs) {
		this.proformasCab = proformasCabs;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}


}