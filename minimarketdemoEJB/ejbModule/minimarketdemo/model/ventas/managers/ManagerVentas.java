package minimarketdemo.model.ventas.managers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import minimarketdemo.model.auditoria.managers.ManagerAuditoria;
import minimarketdemo.model.core.entities.Persona;
import minimarketdemo.model.core.entities.Producto;
import minimarketdemo.model.core.entities.ProformaDet;
import minimarketdemo.model.core.entities.ProformasCab;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.core.managers.ManagerDAO;
import minimarketdemo.model.seguridades.dtos.LoginDTO;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;

/**
 * Session Bean implementation class ManagerProyectos
 */
@Stateless
@LocalBean
public class ManagerVentas {

	// Nuevo codigo auditoria
	@EJB
	private ManagerAuditoria mAuditoria;

	@EJB
	private ManagerDAO mDAO;

	

	public ManagerVentas() {
	}

	// Inicializadores
		// Persona
		public Persona inicializarPersona() {
			Persona persona = new Persona();
			persona.setCedula("");
			persona.setNombre("");
			persona.setApellido("");
			persona.setDireccion("");
			persona.setTelefono("");
			persona.setCorreo("");
			persona.setEstadoPersona(true);
			
			return persona;
		}

		// Funciones del Administrador de proveedores
		// Listar personas
		public List<Persona> findAllPersonas() {
			return mDAO.findAll(Persona.class);
		}

		// Insercion de Personas
		public void insertarPersona(LoginDTO loginDTO, Persona nuevaPersona) throws Exception {
			mDAO.insertar(nuevaPersona);
			// Nuevo codigo auditoria
			// Forma simple
			// mostrarLog(getClass(), "insertar Persona", "Persona:
			// "+nuevaPersona.getProvCiuNombre()+ " agregada con �xito"); Usar reflexion
			// para ingreso automatico
			// Forma compuesta
		//	mAuditoria.mostrarLog(loginDTO, getClass(), "insertarPersona", "Persona: " + nuevaPersona.getApellido() + " "
				//	+ nuevaPersona.getNombre() + " agregada con �xito");

		}

		// Actualizacion de Personas
		public void actualizarPersona(LoginDTO loginDTO, Persona edicionPersona) throws Exception {
			Persona persona = (Persona) mDAO.findById(Persona.class, edicionPersona.getIdPersona()); // Buscar el persona a	editar																					// editar
			persona.setNombre(edicionPersona.getNombre());
			persona.setApellido(edicionPersona.getApellido());
			persona.setDireccion(edicionPersona.getDireccion());
			
			persona.setTelefono(edicionPersona.getTelefono());
			persona.setCorreo(edicionPersona.getCorreo());
			persona.setEstadoPersona(edicionPersona.getEstadoPersona());
			mDAO.actualizar(persona);
			mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarPersona",
					"se actualiz� la persona " + edicionPersona.getApellido() + " " + edicionPersona.getNombre());
		}
		//Activar/Desactivar
	    public void activarDesactivarPersona(int idPersona) throws Exception {
	    	Persona persona=(Persona) mDAO.findById(Persona.class, idPersona);
	    	persona.setEstadoPersona(!persona.getEstadoPersona());
	    	System.out.println("activar/desactivar "+persona.getEstadoPersona());
	    	mDAO.actualizar(persona);
	    }
	    

		// Borrar de Personas
		public void eliminarPersona(int idPersona) throws Exception {
			Persona persona = (Persona) mDAO.findById(Persona.class, idPersona); //Encontrar el persona a eliminar
			//if (persona.getProformasCabs().size()>0)
			//	throw new Exception("No se puede elimininar el persona porque tiene proformas registradas.");
			mDAO.eliminar(Persona.class, persona.getIdPersona());
			// TODO agregar uso de LoginDTO para auditar metodo.
		}

////////--------------------PROFORMAS CABECERA---------------------------------------------------------

	// Inicializar proformas cab

	public ProformasCab inicializarProformasCab() {
		ProformasCab proformasCab = new ProformasCab();
		proformasCab.setPersona(new Persona()); // prueba
		proformasCab.setPfCabFecha(new Date());
		proformasCab.setPfCabSubtototal(new BigDecimal(0)); 
		proformasCab.setPfCabIva(new BigDecimal(0));
		proformasCab.setPfCabTotal(new BigDecimal(0));
		return proformasCab;
	}

	// Listar proformas cab
	public List<ProformasCab> findAllProformasCabs() {
		return mDAO.findAll(ProformasCab.class);
	}

	// Insercion de Proformas Cab
	public void insertarProformasCab(LoginDTO loginDTO, ProformasCab nuevaProformasCab, int personaSeleccionada)
			throws Exception {

		Persona persona = (Persona) mDAO.findById(Persona.class, personaSeleccionada); // Encontrar el cliente
		nuevaProformasCab.setPersona(persona);

		mDAO.insertar(nuevaProformasCab);
		// Nuevo codigo auditoria
		// Forma simple
		// mostrarLog(getClass(), "insertar Cliente", "Cliente:
		// "+nuevaCliente.getProvCiuNombre()+ " agregada con �xito"); Usar reflexion
		// para ingreso automatico
		// Forma compuesta
		mAuditoria.mostrarLog(loginDTO, getClass(), "insertarProformasCab",
				"ProformasCab: " + nuevaProformasCab.getPfCabId() + " agregada con �xito");
	}

	// Actualizacion de ProformasCab
	public void actualizarProformasCab(LoginDTO loginDTO, ProformasCab edicionProformasCab) throws Exception {
		ProformasCab proformasCab = (ProformasCab) mDAO.findById(ProformasCab.class, edicionProformasCab.getPfCabId()); // Buscar el cliente

		proformasCab.setPersona(edicionProformasCab.getPersona());
		
		mDAO.actualizar(proformasCab);
		mAuditoria.mostrarLog(loginDTO, getClass(), "actualizarProformasCab",
				"se actualiz� a ProformasCab " + edicionProformasCab.getPfCabId());
	}

	// Borracion de ProformasCab
	public void eliminarProformasCab(int ProformasCabId) throws Exception {
		ProformasCab proformasCab = (ProformasCab) mDAO.findById(ProformasCab.class, ProformasCabId); // Encontrar el
																										// cliente a
																										// eliminar
		//if (proformasCab.getProformaDets().size() > 0)
		//	throw new Exception("No se puede elimininar la proforma porque tiene productos registrados.");
		//mDAO.eliminar(ProformasCab.class, proformasCab.getPfCabId());
		// TODO agregar uso de LoginDTO para auditar metodo.
	}

	//--------------------------------------PRODUCTOS-----------------------------------
	
		//Listar productos
		public List<Producto> findAllProductos(){
			return mDAO.findAll(Producto.class);
		}
		
		// -------------------------------------PROFORMAS-DETALLE---------------------------


		//Listar detalles segun proforma
		public List<ProformaDet> findDetalleByProforma(int proformaId){
	    	return mDAO.findWhere(ProformaDet.class, "o.proformasCab.pfCabId="+proformaId, "o.pfDetId");
	    }
		
		// Inicializar
		public ProformaDet inicializarProformasDet(ProformasCab proformasCab) {
			ProformaDet proformasDet = new ProformaDet();

			proformasDet.setProformasCabs(proformasCab);
			proformasDet.setProducto(new Producto());
			proformasDet.setPfDetCantidad(0);
			proformasDet.setPfDetPrecio(new BigDecimal(0));
			proformasDet.setPfDetPreciototal(new BigDecimal(0));
			//proformasDet.setProformasCab(proformasCab); // Inicializamos con el ID de la proforma Cab
			//proformasDet.setProducto(new Producto());
			//proformasDet.setProDetCantidad(0);
			//proformasDet.setProDetPrecio(new BigDecimal(0));
			//proformasDet.setProDetPreciototal(new BigDecimal(0));
			return proformasDet;
		}

		// Insercion de Proformas Cab
		public void insertarProformasDet(LoginDTO loginDTO, ProformaDet nuevaProformasDet,int productoSeleccionado) throws Exception {

			Producto producto = (Producto) mDAO.findById(Producto.class, productoSeleccionado); // Encontrar la proforma
			nuevaProformasDet.setProducto(producto);
			nuevaProformasDet.setPfDetPrecio(producto.getPrecioCompraProd());
			nuevaProformasDet.setPfDetPreciototal(
					calculoPrecioTotal(producto.getPrecioCompraProd(), nuevaProformasDet.getPfDetCantidad()));
			//nuevaProformasDet.setProDetPrecio(producto.getProducPreciou());
			//nuevaProformasDet.setProDetPreciototal(
					//calculoPrecioTotal(producto.getProducPreciou(), nuevaProformasDet.getProDetCantidad()));

			mDAO.insertar(nuevaProformasDet);
			// Forma compuesta
			mAuditoria.mostrarLog(loginDTO, getClass(), "insertarProformasDet",	"Detalle: " + nuevaProformasDet.getPfDetId() + " agregada con �xito");
			//Actualizar total de proforma cabecera
			calcularTotalProforma(nuevaProformasDet.getProformasCabs().getPfCabId());
		}
		
		
		//Metodo para actualizar Total de ProformasCabecera
		public void calcularTotalProforma(int proformaId) throws Exception {
			
			//Buscar proforma
	    	ProformasCab proformaCab=(ProformasCab) mDAO.findById(ProformasCab.class, proformaId);
	    	//Agregar a una lista los detalles de dicha proforma
	    	List<ProformaDet> detalles=findDetalleByProforma(proformaId);
	    	double suma=0;
	    	for(ProformaDet d:detalles) {
	    		suma+=d.getPfDetPreciototal().doubleValue();
	    	}
	    	double iva = suma*0.12;
	    	double TotalFinal = suma + iva;
	    	
	    	BigDecimal sumaT = new BigDecimal(suma);
	    	BigDecimal ivaT = new BigDecimal(iva);
	    	BigDecimal TotalFinalT = new BigDecimal(TotalFinal);
	    	System.out.println("suma total:"+sumaT);
	    	proformaCab.setPfCabSubtototal(sumaT);
	    	proformaCab.setPfCabIva(ivaT);
	    	proformaCab.setPfCabTotal(TotalFinalT);
	    	
	    	mDAO.actualizar(proformaCab);
	    }
		
		public BigDecimal calculoPrecioTotal(BigDecimal precioU, int cant) {
			double precio_unitario = precioU.doubleValue();
			double precioTotal = precio_unitario * cant;

			BigDecimal precioT = new BigDecimal(precioTotal);
			return precioT;
		}
		
	

}
