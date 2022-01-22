package minimarketdemo.model.proyectos.managers;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import minimarketdemo.model.core.entities.PryProyecto;
import minimarketdemo.model.core.entities.PryTarea;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.core.managers.ManagerDAO;
import minimarketdemo.model.core.utils.ModelUtil;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;

/**
 * Session Bean implementation class ManagerProyectos
 */
@Stateless
@LocalBean
public class ManagerProyectos {
	@EJB
	private ManagerDAO mDAO;

	

	/**
	 * Default constructor.
	 */
	public ManagerProyectos() {

		// FUNCIONES DEL LIDER DE PROYECTOS
	}

	public List<PryProyecto> findAllProyectos() {
		return mDAO.findAll(PryProyecto.class);

	}

	public PryProyecto inicializarProyecto() {
		PryProyecto proyecto = new PryProyecto();
		proyecto.setAvance(0);
		proyecto.setEstado("I");
		proyecto.setFechaInicio(new Date());
		proyecto.setFechaFin(ModelUtil.addDays(new Date(), 30));
		return proyecto;
	}

	public void insertarProyecto(PryProyecto nuevoProyecto) throws Exception {
		mDAO.insertar(nuevoProyecto);
	}

	public List<PryTarea> findTareasByProyecto(int idPryProyecto) {
		return mDAO.findWhere(PryTarea.class, "o.pryProyecto.idPryProyecto=" + idPryProyecto, "o.fechaInicio");
	}

	public PryTarea inicializarTarea(PryProyecto proyecto) {
		PryTarea tarea = new PryTarea();
		tarea.setAvance(0);
		tarea.setFechaInicio(proyecto.getFechaInicio());
		tarea.setFechaFin(proyecto.getFechaFin());
		tarea.setPryProyecto(proyecto);
		return tarea;
	}
	public void insertarTarea(PryTarea nuevaTarea , int idSegUsuario)throws Exception{
		SegUsuario usuario=(SegUsuario) mDAO.findById(SegUsuario.class, idSegUsuario);
		nuevaTarea.setSegUsuario(usuario);
		mDAO.insertar(nuevaTarea);
	}
	//FUNCIONES DEL ANALISTA
	public List<PryTarea> findTareasByUsuario(int idSegUsuario){
		return mDAO.findWhere(PryTarea.class, "o.SegUsuario.idSegUsuario="+idSegUsuario, "o.fechaInicio");
			
	}
	public void actualizarAvance(PryTarea Tarea) throws Exception {
    	mDAO.actualizar(Tarea);
    	List<PryTarea> listaTareas=findTareasByProyecto(Tarea.getPryProyecto().getIdPryProyecto());
    	int suma=0;
    	for(PryTarea t:listaTareas) {
    		suma+=t.getAvance();
    	}
    	int promedio=suma/listaTareas.size();
    	PryProyecto proyecto=(PryProyecto) mDAO.findById(PryProyecto.class, Tarea.getPryProyecto().getIdPryProyecto());
    	proyecto.setAvance(promedio);
    	if(promedio==100) {
    	proyecto.setEstado("F");	
    	}else {
        	proyecto.setEstado("D");	
    	}
    	mDAO.actualizar(proyecto);
    }
    
    public void actualizarEstado(PryProyecto nuevoEstado) throws Exception {
    	PryProyecto proyecto=(PryProyecto) mDAO.findById(PryProyecto.class, nuevoEstado.getIdPryProyecto());
    	String estado= nuevoEstado.getEstado();
    	proyecto.setEstado(estado);
    	mDAO.actualizar(proyecto);
    }
}


