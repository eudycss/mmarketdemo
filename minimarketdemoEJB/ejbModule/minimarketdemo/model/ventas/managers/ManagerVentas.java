package minimarketdemo.model.ventas.managers;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import minimarketdemo.model.auditoria.managers.ManagerAuditoria;
import minimarketdemo.model.core.entities.Persona;
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

	

}
