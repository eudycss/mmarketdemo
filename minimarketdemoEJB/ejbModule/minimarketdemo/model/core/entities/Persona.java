package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the persona database table.
 * 
 */
@Entity
@Table(name="persona")
@NamedQuery(name="Persona.findAll", query="SELECT p FROM Persona p")
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_persona", unique=true, nullable=false)
	private Integer idPersona;

	@Column(nullable=false, length=50)
	private String apellido;

	@Column(nullable=false, length=10)
	private String cedula;

	@Column(nullable=false, length=50)
	private String correo;

	@Column(nullable=false, length=50)
	private String direccion;

	@Column(name="estado_persona", nullable=false)
	private Boolean estadoPersona;

	@Column(nullable=false, length=50)
	private String nombre;

	@Column(nullable=false, length=10)
	private String telefono;

	//bi-directional many-to-one association to Proveedore
	@OneToMany(mappedBy="persona")
	private List<Proveedore> proveedores;

	//bi-directional many-to-one association to Salida
	@OneToMany(mappedBy="persona")
	private List<Salida> salidas;

	public Persona() {
	}

	public Integer getIdPersona() {
		return this.idPersona;
	}

	public void setIdPersona(Integer idPersona) {
		this.idPersona = idPersona;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getCorreo() {
		return this.correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getEstadoPersona() {
		return this.estadoPersona;
	}

	public void setEstadoPersona(Boolean estadoPersona) {
		this.estadoPersona = estadoPersona;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public List<Proveedore> getProveedores() {
		return this.proveedores;
	}

	public void setProveedores(List<Proveedore> proveedores) {
		this.proveedores = proveedores;
	}

	public Proveedore addProveedore(Proveedore proveedore) {
		getProveedores().add(proveedore);
		proveedore.setPersona(this);

		return proveedore;
	}

	public Proveedore removeProveedore(Proveedore proveedore) {
		getProveedores().remove(proveedore);
		proveedore.setPersona(null);

		return proveedore;
	}

	public List<Salida> getSalidas() {
		return this.salidas;
	}

	public void setSalidas(List<Salida> salidas) {
		this.salidas = salidas;
	}

	public Salida addSalida(Salida salida) {
		getSalidas().add(salida);
		salida.setPersona(this);

		return salida;
	}

	public Salida removeSalida(Salida salida) {
		getSalidas().remove(salida);
		salida.setPersona(null);

		return salida;
	}

}