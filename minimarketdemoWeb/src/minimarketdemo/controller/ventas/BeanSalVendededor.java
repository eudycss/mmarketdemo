package minimarketdemo.controller.ventas;

import java.io.Serializable;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.sun.jdi.connect.spi.Connection;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.controller.seguridades.BeanSegLogin;
import minimarketdemo.model.core.entities.Persona;

import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;
import minimarketdemo.model.ventas.managers.ManagerVentas;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named
@SessionScoped
public class BeanSalVendededor implements Serializable {

	
	@EJB
	private ManagerVentas mVentas;
	private List<Persona> listaPersonas;

	// Variables
	private Persona nuevaPersona;
	private Persona edicionPersona;
	
	@Inject
	private BeanSegLogin beanSegLogin;

	public BeanSalVendededor() {
	}

	@PostConstruct
	public void inicializar() {
		listaPersonas = mVentas.findAllPersonas();
		nuevaPersona = mVentas.inicializarPersona();
	}

	// ----------------Inserccion
	//Agregar Persona
	public void actionListenerInsertarPersona() {
		try {
			mVentas.insertarPersona(beanSegLogin.getLoginDTO(), nuevaPersona);
			System.out.println("nueva persona: "+nuevaPersona);
			JSFUtil.crearMensajeINFO("Persona agregada con éxito");
			listaPersonas = mVentas.findAllPersonas();
			nuevaPersona = mVentas.inicializarPersona();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}

	//Cargar pagina de Agregar Personas
	public String actionCargarAgregarPersonas() {
		nuevaPersona = mVentas.inicializarPersona();
		return "personas_nuevo";

	}
	
	//-----------------Edicion
	//Actualizar Persona
	public void actionListenerActualizarEdicionPersona() {
		try {
			mVentas.actualizarPersona(beanSegLogin.getLoginDTO(),edicionPersona);
			listaPersonas=mVentas.findAllPersonas();
			JSFUtil.crearMensajeINFO("Persona actualizado.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	// Cargar pagina de Editar Persona
	public String actionSeleccionarEdicionPersona(Persona persona) {
		edicionPersona=persona;
		return "personas_edicion";
	}
	//Activar/Desactivar Persona
	public void actionListenerActivarDesactivarPersona(int idPersona) {
		try {
			mVentas.activarDesactivarPersona(idPersona);
			listaPersonas=mVentas.findAllPersonas();
			JSFUtil.crearMensajeINFO("Persona activado/desactivado");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	// ---------------- Borración
	
	public void actionListenerEliminarPersona(int idPersona) {
		try {
			mVentas.eliminarPersona(idPersona);
			listaPersonas=mVentas.findAllPersonas();
			JSFUtil.crearMensajeINFO("Persona eliminada.");
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Reporte
	
	public String actionReporte(){
		Map<String,Object> parametros=new HashMap<String,Object>();
		FacesContext context=FacesContext.getCurrentInstance();
		ServletContext servletContext=(ServletContext)context.getExternalContext().getContext();
		String ruta=servletContext.getRealPath("salida/vendedor/reporte02.jasper");
		System.out.println(ruta);
		HttpServletResponse response=(HttpServletResponse)context.getExternalContext().getResponse();
		response.addHeader("Content-disposition", "attachment;filename=reporte.pdf");
		response.setContentType("application/pdf");
		try {
		Class.forName("org.postgresql.Driver");
		java.sql.Connection connection = null;
		connection= DriverManager.getConnection("jdbc:postgresql://localhost:5432/mmarketdemo","sebas", "sebas123");
		 //DriverManager.getConnection("jdbc:postgresql://localhost:5432/mmarketdemo","sebas", "sebas123");
		JasperPrint impresion=JasperFillManager.fillReport(ruta, parametros, connection);
		JasperExportManager.exportReportToPdfStream(impresion, response.getOutputStream());
		context.getApplication().getStateManager().saveView(context);
		System.out.println("reporte generado.");
		context.responseComplete();
		} catch (Exception e) {
		JSFUtil.crearMensajeERROR(e.getMessage());
		e.printStackTrace();
		}
		return "";
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

	// ACCESORES


	
	
	
	
	
	
}
