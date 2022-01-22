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
public class BeanSalSecretaria implements Serializable {
@EJB
private ManagerSalud mSalud;
// Variable para la lista de carnet y no variable para cada cosa
//Entity para no crear variable para cada campo

private  SalCarnet carnetNuevo;
private List<SalCarnet> listaCarnet;
	public BeanSalSecretaria() {
  // No usar el constructor
	}
	@PostConstruct
	public void inicializar() {
		carnetNuevo= new SalCarnet();
		//Cada vez que se inserte sale la lista
		listaCarnet=mSalud.findAllCarnet();
	}

	public void actionListenerInsertarCarnet() {
		mSalud.insertarCarnet(carnetNuevo);
		carnetNuevo=new SalCarnet(); // para vaciar los campos
		listaCarnet=mSalud.findAllCarnet();
		JSFUtil.crearMensajeINFO("Carnet guardado");
	}
	public SalCarnet getCarnetNuevo() {
		return carnetNuevo;
	}

	public void setCarnetNuevo(SalCarnet carnetNuevo) {
		this.carnetNuevo = carnetNuevo;
	}
	public List<SalCarnet> getListaCarnet() {
		return listaCarnet;
	}
	public void setListaCarnet(List<SalCarnet> listaCarnet) {
		this.listaCarnet = listaCarnet;
	}
	

}
