package minimarketdemo.controller.salud;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.core.entities.SalCarnet;
import minimarketdemo.model.salud.managers.ManagerSalud;

@Named
@SessionScoped
public class BeanSalEnfermera implements Serializable {
@EJB
private ManagerSalud mSalud;
private List<SalCarnet> listaCarnet;
private int idSalCarnet;

	public BeanSalEnfermera() {

	}
	
	@PostConstruct
	public void inicializar() {
		listaCarnet=mSalud.findAllCarnet();
	}
	
	public void actionListenerValidar() {
		mSalud.validarCarnet(idSalCarnet);
		listaCarnet=mSalud.findAllCarnet();
		JSFUtil.crearMensajeINFO("El carnet ha sido validado");
	}

	public List<SalCarnet> getListaCarnet() {
		return listaCarnet;
	}

	public void setListaCarnet(List<SalCarnet> listaCarnet) {
		this.listaCarnet = listaCarnet;
	}

	public int getIdSalCarnet() {
		return idSalCarnet;
	}

	public void setIdSalCarnet(int idSalCarnet) {
		this.idSalCarnet = idSalCarnet;
	}

	
	
}
