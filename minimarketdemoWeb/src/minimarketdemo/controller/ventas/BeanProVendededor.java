	package minimarketdemo.controller.ventas;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.controller.seguridades.BeanSegLogin;
import minimarketdemo.model.core.entities.Persona;
import minimarketdemo.model.core.entities.Producto;
import minimarketdemo.model.core.entities.ProformaDet;
import minimarketdemo.model.core.entities.ProformasCab;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;
import minimarketdemo.model.ventas.managers.ManagerVentas;

@Named
@SessionScoped
public class BeanProVendededor implements Serializable {

	@EJB
	private ManagerSeguridades mSeg;
	@EJB
	private ManagerVentas mVentas;
	private List<Persona> listaPersonas;
	private List<ProformasCab> listaProformasCab;
	private List<ProformaDet> listaProformasDet;
	private List<Producto> listaProductos;

	// Variables Persona
	private Persona nuevaPersona;
	private Persona edicionPersona;
	
	//Variables ProformasCab
	private  int personaSeleccionada;
	private ProformasCab nuevaProformasCab;
	private ProformasCab edicionProformasCab;
	
	// Variables ProformasDet
	private ProformasCab proformaCabSeleccionada;
	private int productoSeleccionado;
	
	private ProformaDet nuevaProformaDet;
	private ProformaDet edicionProformaDet;
	
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanProVendededor() {
	}

	@PostConstruct
	public void inicializar() {
		listaPersonas = mVentas.findAllPersonas();
		nuevaPersona = mVentas.inicializarPersona();
		
		listaProformasCab=mVentas.findAllProformasCabs();
		nuevaProformasCab=mVentas.inicializarProformasCab();
	}

	// ----------------Inserccion
	//Agregar Proforma
	public void actionListenerInsertarProformasCab() {
		try {
			System.out.println("nueva proforma: "+nuevaProformasCab);
			mVentas.insertarProformasCab(beanSegLogin.getLoginDTO(), nuevaProformasCab, personaSeleccionada);
			
			JSFUtil.crearMensajeINFO("Proforma agregada con éxito");
			listaProformasCab = mVentas.findAllProformasCabs();
			nuevaProformasCab = mVentas.inicializarProformasCab();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	//Cargar pagina de Agregar Proforma cab
	public String actionCargarAgregarProforma() {
		nuevaProformasCab= mVentas.inicializarProformasCab();
		return "proformas_nuevo";

	}
	
	//-----------------Edicion
	//Actualizar Proforma
	public void actionListenerActualizarEdicionProformasCab() {
		try {
			mVentas.actualizarProformasCab(beanSegLogin.getLoginDTO(),edicionProformasCab);
			listaProformasCab=mVentas.findAllProformasCabs();
			JSFUtil.crearMensajeINFO("Proforma Cab actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	// Cargar pagina de Editar Proforma
	public String actionSeleccionarEdicionProformasCab(ProformasCab proformasCab) {
		edicionProformasCab=proformasCab;
		return "proformasCab_edicion";
	}

	
	
	// ---------------- Borrar
	
	public void actionListenerEliminarProformasCab(int idProformaCab) {
		try {
			mVentas.eliminarProformasCab(idProformaCab);
			listaProformasCab=mVentas.findAllProformasCabs();
			JSFUtil.crearMensajeINFO("Proforma eliminada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
		public List<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public Persona getNuevaPersona() {
		return nuevaPersona;
	}

	public void setNuevaPersona(Persona nuevaPersona) {
		this.nuevaPersona = nuevaPersona;
	}

	public Persona getEdicionPersona() {
		return edicionPersona;
	}

	public void setEdicionPersona(Persona edicionPersona) {
		this.edicionPersona = edicionPersona;
	}

	public List<ProformasCab> getListaProformasCab() {
		return listaProformasCab;
	}

	public void setListaProformasCab(List<ProformasCab> listaProformasCab) {
		this.listaProformasCab = listaProformasCab;
	}

	public int getPersonaSeleccionada() {
		return personaSeleccionada;
	}

	public void setPersonaSeleccionada(int personaSeleccionada) {
		this.personaSeleccionada = personaSeleccionada;
	}

	public ProformasCab getNuevaProformasCab() {
		return nuevaProformasCab;
	}

	public void setNuevaProformasCab(ProformasCab nuevaProformasCab) {
		this.nuevaProformasCab = nuevaProformasCab;
	}

	public ProformasCab getEdicionProformasCab() {
		return edicionProformasCab;
	}

	public void setEdicionProformasCab(ProformasCab edicionProformasCab) {
		this.edicionProformasCab = edicionProformasCab;
	}

	public ProformaDet getNuevaProformaDet() {
		return nuevaProformaDet;
	}

	public void setNuevaProformaDet(ProformaDet nuevaProformaDet) {
		this.nuevaProformaDet = nuevaProformaDet;
	}

	public ProformaDet getEdicionProformaDet() {
		return edicionProformaDet;
	}

	public void setEdicionProformaDet(ProformaDet edicionProformaDet) {
		this.edicionProformaDet = edicionProformaDet;
	}

	public ProformasCab getProformaCabSeleccionada() {
		return proformaCabSeleccionada;
	}

	public void setProformaCabSeleccionada(ProformasCab proformaCabSeleccionada) {
		this.proformaCabSeleccionada = proformaCabSeleccionada;
	}

	public int getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(int productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public List<ProformaDet> getListaProformasDet() {
		return listaProformasDet;
	}

	public void setListaProformasDet(List<ProformaDet> listaProformasDet) {
		this.listaProformasDet = listaProformasDet;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}

	// ACCESORES


	
	
	
	
	
	
}
