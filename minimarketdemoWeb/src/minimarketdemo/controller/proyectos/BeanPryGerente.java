package minimarketdemo.controller.proyectos;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.controller.seguridades.BeanSegLogin;
import minimarketdemo.model.core.entities.PryProyecto;
import minimarketdemo.model.core.entities.PryTarea;
import minimarketdemo.model.proyectos.managers.ManagerProyectos;

@Named
@SessionScoped
public class BeanPryGerente implements Serializable {
	@EJB
	private ManagerProyectos mProyectos;
	private List<PryProyecto> listaProyectos;
	private PryProyecto nuevoEstado;
	@Inject
	private BeanSegLogin beanSegLogin;
	public BeanPryGerente() {
		// TODO Auto-generated constructor stub
	}
@PostConstruct
public void inicializar() {
	listaProyectos=mProyectos.findAllProyectos();
}

public void actionListenerActualizarEstado(PryProyecto Estado) {
	try {
		mProyectos.actualizarEstado(Estado);
		JSFUtil.crearMensajeINFO("Estado actualizado");
	} catch (Exception e) {
		JSFUtil.crearMensajeERROR(e.getMessage());
		e.printStackTrace();
	}
}
public List<PryProyecto> getListaProyectos() {
	return listaProyectos;
}
public void setListaProyectos(List<PryProyecto> listaProyectos) {
	this.listaProyectos = listaProyectos;
}
public PryProyecto getNuevoEstado() {
	return nuevoEstado;
}
public void setNuevoEstado(PryProyecto nuevoEstado) {
	this.nuevoEstado = nuevoEstado;
}
public BeanSegLogin getBeanSegLogin() {
	return beanSegLogin;
}
public void setBeanSegLogin(BeanSegLogin beanSegLogin) {
	this.beanSegLogin = beanSegLogin;
}




}
