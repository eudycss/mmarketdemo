package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the proveedores database table.
 * 
 */
@Entity
@Table(name="proveedores")
@NamedQuery(name="Proveedore.findAll", query="SELECT p FROM Proveedore p")
public class Proveedore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_proveedor", unique=true, nullable=false)
	private Integer idProveedor;

	@Column(nullable=false, length=50)
	private String descripcion;

	@Column(nullable=false)
	private Boolean estado;

	@Column(name="marca_prov", nullable=false, length=50)
	private String marcaProv;

	//bi-directional many-to-one association to Ingreso
	@OneToMany(mappedBy="proveedore")
	private List<Ingreso> ingresos;

	//bi-directional many-to-one association to Persona
	//@ManyToOne
	//@JoinColumn(name="id_persona")
	//bi-directional many-to-one association to Producto
	//@OneToMany(mappedBy="proveedore")
	//private List<Producto> productos;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="id_persona", nullable=false)
	private Persona persona;

	public Proveedore() {
	}

	public Integer getIdProveedor() {
		return this.idProveedor;
	}

	public void setIdProveedor(Integer idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getEstado() {
		return this.estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getMarcaProv() {
		return this.marcaProv;
	}

	public void setMarcaProv(String marcaProv) {
		this.marcaProv = marcaProv;
	}

	public List<Ingreso> getIngresos() {
		return this.ingresos;
	}

	public void setIngresos(List<Ingreso> ingresos) {
		this.ingresos = ingresos;
	}

	public Ingreso addIngreso(Ingreso ingreso) {
		getIngresos().add(ingreso);
		ingreso.setProveedore(this);

		return ingreso;
	}

	public Ingreso removeIngreso(Ingreso ingreso) {
		getIngresos().remove(ingreso);
		ingreso.setProveedore(null);

		return ingreso;
	}

	public Persona getPersona() {
		return this.persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

}