package minimarketdemo.model.ventas.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import minimarketdemo.model.auditoria.managers.ManagerAuditoria;
import minimarketdemo.model.core.entities.Persona;
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
			// "+nuevaPersona.getProvCiuNombre()+ " agregada con éxito"); Usar reflexion
			// para ingreso automatico
			// Forma compuesta
		//	mAuditoria.mostrarLog(loginDTO, getClass(), "insertarPersona", "Persona: " + nuevaPersona.getApellido() + " "
				//	+ nuevaPersona.getNombre() + " agregada con éxito");

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
					"se actualizó la persona " + edicionPersona.getApellido() + " " + edicionPersona.getNombre());
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

	

}
