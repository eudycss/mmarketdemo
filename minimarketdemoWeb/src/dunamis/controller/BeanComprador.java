package dunamis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import dunamis.model.managers.ManagerCompras;

import minimarketdemo.model.core.entities.Persona;
import minimarketdemo.model.core.entities.Producto;
import minimarketdemo.model.core.entities.ProformaDet;
import minimarketdemo.model.core.entities.ProformasCab;



@Named
@SessionScoped
public class BeanComprador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ManagerCompras mProformasCabs;

	private List<Producto> listaProductos;
	private List<Persona> listaPersonas;
	private List<ProformasCab> listaProformasCabs;

	// ProformasDetalle
	private ProformaDet nuevoDetalle;
	private int productoIngreso;
	private int cantidadIngreso;
	private double precioIngreso;
	private double totalDetalle;
	// ProformasCabsCabecera
	private ProformasCab nuevaProformasCab;
	private int idprofromaCabIngreso;
	private Date fechaIngreso;
	private int personaIngreso;
	private double subtotalIngreso;
	
	private double ivaIngreso;
	private double totalIngreso;

	// Agregar Detalles
	private List<ProformaDet> listaDetallesProformasCab;

	public BeanComprador() {
	}
	
	@PostConstruct
	public void inicializar() {
		listaProductos = mProformasCabs.findAllProductos();
		listaPersonas = mProformasCabs.findAllPersonas();
		listaProformasCabs = mProformasCabs.findAllProformasCabs();
		listaDetallesProformasCab = new ArrayList<ProformaDet>();
	}

	// ----------------------------COMPRAS MULTIPLES--------------------------------
	public void actionListenerCrearProformaDet1() throws Exception {
		ProformaDet detalle=(ProformaDet) mProformasCabs.crearDetalleCompra1(productoIngreso, cantidadIngreso, precioIngreso);
		listaDetallesProformasCab.add(detalle);
		totalDetalle = mProformasCabs.totalDetalleCompra(listaDetallesProformasCab);
	}

	public void actionListenerRegistrarProformasCab1() throws Exception {
		mProformasCabs.registrarCompra1(listaDetallesProformasCab, personaIngreso, fechaIngreso, ivaIngreso, subtotalIngreso); 
				
		JSFUtil.crearMensajeInfo("Se guardó la proformasCab exitosamente");
		listaProformasCabs = mProformasCabs.findAllProformasCabs();
		listaDetallesProformasCab = new ArrayList<ProformaDet>();
		totalDetalle = 0;
	}
	// ---------------------------COMPRAS MAESTRO-DETALLE---------------------------

	// Agregar detalle
	public void actionListenerAgregarProformaDet() throws Exception {

		// double stockDisponible = mProformasCabs.calcularStockInicial(productoIngreso);
		// double resta = stockDisponible - cantidadIngreso;

		// if (resta > 0) {
		//nuevaProformasCab = mProformasCabs.crearProformaDet1(nuevaProformasCab, productoIngreso, cantidadIngreso, precioIngreso);
		//totalDetalle = mProformasCabs.ComCabSubtotal(nuevaProformasCab);
		//listaDetalle = nuevaProformasCab.getProformasCabsDets();
		// } else {
		// JSFUtil.crearMensajeERROR("No existe suficientes productos");
		// }
	}

	// Guardar proformasCab
	public void actionListenerGuardarProformasCab() {
		try {
			//mProformasCabs.registrarProformasCab(nuevaProformasCab, personaIngreso, fechaIngreso, totalDetalle, ivaIngreso);
			JSFUtil.crearMensajeInfo("Se guardó la proformasCab exitosamente");
			//nuevaProformasCab = new ProformasCabsCab();
			//listaProductos = mProformasCabs.findAllProductos();
			//listaProformasCabsCab = mProformasCabs.findAllProformasCabsCab();
		} catch (Exception e) {
			e.printStackTrace();
		}
		totalDetalle = 0;
		personaIngreso = 0;
		fechaIngreso = new Date();
		totalDetalle = 0;

	}
	
	public void actionListenerEliminarProformaCab1(int proformaId) {
		try {
			mProformasCabs.eliminarProformasCab1(proformaId);
//			mVentas.eliminarOrden(ordenId);
			listaProformasCabs=mProformasCabs.findAllProformasCabs();

			JSFUtil.crearMensajeInfo("Proforma Cabecera eliminada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeError("Proforma no eliminada");
			e.printStackTrace();
		}
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void setListaProductos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}



	public List<ProformasCab> getListaProformasCabs() {
		return listaProformasCabs;
	}

	public void setListaProformasCabs(List<ProformasCab> listaProformasCabs) {
		this.listaProformasCabs = listaProformasCabs;
	}

	public ProformaDet getNuevoDetalle() {
		return nuevoDetalle;
	}

	public void setNuevoDetalle(ProformaDet nuevoDetalle) {
		this.nuevoDetalle = nuevoDetalle;
	}

	public int getProductoIngreso() {
		return productoIngreso;
	}

	public void setProductoIngreso(int productoIngreso) {
		this.productoIngreso = productoIngreso;
	}

	public int getCantidadIngreso() {
		return cantidadIngreso;
	}

	public void setCantidadIngreso(int cantidadIngreso) {
		this.cantidadIngreso = cantidadIngreso;
	}

	public double getPrecioIngreso() {
		return precioIngreso;
	}

	public void setPrecioIngreso(double precioIngreso) {
		this.precioIngreso = precioIngreso;
	}

	public double getTotalDetalle() {
		return totalDetalle;
	}

	public void setTotalDetalle(double totalDetalle) {
		this.totalDetalle = totalDetalle;
	}

	public ProformasCab getNuevaProformasCab() {
		return nuevaProformasCab;
	}

	public void setNuevaProformasCab(ProformasCab nuevaProformasCab) {
		this.nuevaProformasCab = nuevaProformasCab;
	}


	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public int getPersonaIngreso() {
		return personaIngreso;
	}

	public void setPersonaIngreso(int personaIngreso) {
		this.personaIngreso = personaIngreso;
	}



	public double getIvaIngreso() {
		return ivaIngreso;
	}

	public void setIvaIngreso(double ivaIngreso) {
		this.ivaIngreso = ivaIngreso;
	}

	public List<ProformaDet> getListaDetallesProformasCab() {
		return listaDetallesProformasCab;
	}

	public void setListaDetallesProformasCab(List<ProformaDet> listaDetallesProformasCab) {
		this.listaDetallesProformasCab = listaDetallesProformasCab;
	}

	public List<Persona> getListaPersonas() {
		return listaPersonas;
	}

	public void setListaPersonas(List<Persona> listaPersonas) {
		this.listaPersonas = listaPersonas;
	}

	public int getIdprofromaCabIngreso() {
		return idprofromaCabIngreso;
	}

	public void setIdprofromaCabIngreso(int idprofromaCabIngreso) {
		this.idprofromaCabIngreso = idprofromaCabIngreso;
	}

	public double getSubtotalIngreso() {
		return subtotalIngreso;
	}

	public void setSubtotalIngreso(double subtotalIngreso) {
		this.subtotalIngreso = subtotalIngreso;
	}

	public double getTotalIngreso() {
		return totalIngreso;
	}

	public void setTotalIngreso(double totalIngreso) {
		this.totalIngreso = totalIngreso;
	}

	// ACCESORES
	
	

	
}
