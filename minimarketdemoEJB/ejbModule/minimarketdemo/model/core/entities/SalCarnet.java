package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sal_carnet database table.
 * 
 */
@Entity
@Table(name="sal_carnet")
@NamedQuery(name="SalCarnet.findAll", query="SELECT s FROM SalCarnet s")
public class SalCarnet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_sal_carnet", unique=true, nullable=false)
	private Integer idSalCarnet;

	@Column(name="cedula_estudiante", length=10)
	private String cedulaEstudiante;

	@Temporal(TemporalType.DATE)
	private Date fecha1;

	@Temporal(TemporalType.DATE)
	private Date fecha2;

	private Boolean verificado;

	public SalCarnet() {
	}

	public Integer getIdSalCarnet() {
		return this.idSalCarnet;
	}

	public void setIdSalCarnet(Integer idSalCarnet) {
		this.idSalCarnet = idSalCarnet;
	}

	public String getCedulaEstudiante() {
		return this.cedulaEstudiante;
	}

	public void setCedulaEstudiante(String cedulaEstudiante) {
		this.cedulaEstudiante = cedulaEstudiante;
	}

	public Date getFecha1() {
		return this.fecha1;
	}

	public void setFecha1(Date fecha1) {
		this.fecha1 = fecha1;
	}

	public Date getFecha2() {
		return this.fecha2;
	}

	public void setFecha2(Date fecha2) {
		this.fecha2 = fecha2;
	}

	public Boolean getVerificado() {
		return this.verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}

}