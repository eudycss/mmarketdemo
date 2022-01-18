package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the proformas_cab database table.
 * 
 */
@Entity
@Table(name="proformas_cab")
@NamedQuery(name="ProformasCab.findAll", query="SELECT p FROM ProformasCab p")
public class ProformasCab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pf_cab_id", unique=true, nullable=false)
	private Integer pfCabId;



	@Temporal(TemporalType.DATE)
	@Column(name="pf_cab_fecha", nullable=false)
	private Date pfCabFecha;

	@Column(name="pf_cab_iva", nullable=false, precision=131089)
	private BigDecimal pfCabIva;

	@Column(name="pf_cab_subtototal", nullable=false, precision=131089)
	private BigDecimal pfCabSubtototal;

	@Column(name="pf_cab_total", nullable=false, precision=131089)
	private BigDecimal pfCabTotal;

	//bi-directional many-to-one association to Cliente
		@ManyToOne
		@JoinColumn(name="id_persona", nullable=false)
		private Persona persona;

		//bi-directional many-to-one association to ProformasDet
		@OneToMany(mappedBy="proformasCab")
		private List<ProformaDet> proformaDets;

	public ProformasCab() {
	}

	public Integer getPfCabId() {
		return this.pfCabId;
	}

	public void setPfCabId(Integer pfCabId) {
		this.pfCabId = pfCabId;
	}


	public Date getPfCabFecha() {
		return this.pfCabFecha;
	}

	public void setPfCabFecha(Date pfCabFecha) {
		this.pfCabFecha = pfCabFecha;
	}

	public BigDecimal getPfCabIva() {
		return this.pfCabIva;
	}

	public void setPfCabIva(BigDecimal pfCabIva) {
		this.pfCabIva = pfCabIva;
	}

	public BigDecimal getPfCabSubtototal() {
		return this.pfCabSubtototal;
	}

	public void setPfCabSubtototal(BigDecimal pfCabSubtototal) {
		this.pfCabSubtototal = pfCabSubtototal;
	}

	public BigDecimal getPfCabTotal() {
		return this.pfCabTotal;
	}

	public void setPfCabTotal(BigDecimal pfCabTotal) {
		this.pfCabTotal = pfCabTotal;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public List<ProformaDet> getProformaDets() {
		return proformaDets;
	}

	public void setProformaDets(List<ProformaDet> proformaDets) {
		this.proformaDets = proformaDets;
	}

	
}