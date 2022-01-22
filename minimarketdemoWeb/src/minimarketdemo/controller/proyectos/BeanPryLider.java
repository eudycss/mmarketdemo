package minimarketdemo.controller.proyectos;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.core.entities.PryProyecto;
import minimarketdemo.model.core.entities.PryTarea;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.proyectos.managers.ManagerProyectos;
import minimarketdemo.model.seguridades.managers.ManagerSeguridades;

@Named
@SessionScoped
public class BeanPryLider implements Serializable {
	@EJB
	private ManagerProyectos mProyecto;
	@EJB
	private ManagerSeguridades mSeguridades;
    private PryProyecto nuevoProyecto;
    @EJB
    private ManagerProyectos mProyecto;
    @EJB
    private ManagerSeguridades mSeguridades;
    private PryProyecto nuevoProyecto; // este hay que inicializarlo 
    private List<PryProyecto> listaProyectos;
    private List<PryTarea> listaTareas;
    private PryTarea nuevaTarea;
    private List<SegUsuario> listaUsuarios;
    private int idSegUsuarioSeleccionado;
    private PryProyecto proyectoSeleccionado;
    
    public BeanPryLider() {
		
	}
	@PostConstruct
	public void inicializar() {
		listaProyectos = mProyecto.findAllProyectos();
		nuevoProyecto= mProyecto.inicializarProyecto();
	}

	public void actionListenerInsertarProyecto() {
		try {
			mProyecto.insertarProyecto(nuevoProyecto);
			JSFUtil.crearMensajeINFO("Proyecto creado.");
			listaProyectos= mProyecto.findAllProyectos();
			nuevoProyecto= mProyecto.inicializarProyecto();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}	
	}
	public String actionCargarTareas(PryProyecto proyecto) {
		listaTareas=mProyecto.findTareasByProyecto(proyecto.getIdPryProyecto());
    private int idSegUsarioSeleccionado;
    private PryProyecto proyectoSeleccionado;
    
	public BeanPryLider() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	//Inicializar para ver automaticamente en la pagina
	public void inicializar() {
		listaProyectos=mProyecto.findAllProyectos();
		nuevoProyecto=mProyecto.inicializarProyecto();
	}
	
	public void actionListenerInsertarProyecto() {
		try {
			mProyecto.insertarProyecto(nuevoProyecto);
			JSFUtil.crearMensajeINFO("Proyecto creado");
			listaProyectos=mProyecto.findAllProyectos();
			//Cuando se guardar depues hay que limpiarlo
			nuevoProyecto=mProyecto.inicializarProyecto();
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public String actionCargarTareas(PryProyecto proyecto) {
		listaTareas= mProyecto.findTareasByProyecto(proyecto.getIdPryProyecto());
		nuevaTarea=mProyecto.inicializarTarea(proyecto);
		listaUsuarios=mSeguridades.findAllUsuarios();
		proyectoSeleccionado=proyecto;
		return "tareas";
	}
<<<<<<< HEAD
	public void actionListenerInsertarTarea() {
		try {
			mProyecto.insertarTarea(nuevaTarea, idSegUsuarioSeleccionado);
			JSFUtil.crearMensajeINFO("Tarea creada.");
			listaTareas=mProyecto.findTareasByProyecto(proyectoSeleccionado.getIdPryProyecto());
			nuevaTarea= mProyecto.inicializarTarea(proyectoSeleccionado);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}	
	}
=======
	
	//M�todo que guarda la tarea
	public void actionListenerInsertarTarea() {
		try {
			mProyecto.insertarTarea(nuevaTarea, idSegUsarioSeleccionado);
			JSFUtil.crearMensajeINFO("Tarea creada.");
			listaTareas=mProyecto.findTareasByProyecto(proyectoSeleccionado.getIdPryProyecto());
			
			//Cuando se guardar depues hay que limpiarlo
			nuevaTarea=mProyecto.inicializarTarea(proyectoSeleccionado);
		} catch (Exception e) {
			JSFUtil.crearMensajeERROR(e.getMessage());
			e.printStackTrace();
		}
	}
	
	//Para visualizar e intregrar en la vista hay que crear accesores,
	//menos al manager porque es privado, para el funcionamiento interno del controlador
>>>>>>> 727c903af50055b24c6b3aa8b95b773b94b16352
	public PryProyecto getNuevoProyecto() {
		return nuevoProyecto;
	}
	public void setNuevoProyecto(PryProyecto nuevoProyecto) {
		this.nuevoProyecto = nuevoProyecto;
	}
	public List<PryProyecto> getListaProyectos() {
		return listaProyectos;
	}
	public void setListaProyectos(List<PryProyecto> listaProyectos) {
		this.listaProyectos = listaProyectos;
	}
	public List<PryTarea> getListaTareas() {
		return listaTareas;
	}
	public void setListaTareas(List<PryTarea> listaTareas) {
		this.listaTareas = listaTareas;
	}
	public PryTarea getNuevaTarea() {
		return nuevaTarea;
	}
	public void setNuevaTarea(PryTarea nuevaTarea) {
		this.nuevaTarea = nuevaTarea;
	}
	public List<SegUsuario> getListaUsuarios() {
		return listaUsuarios;
	}
	public void setListaUsuarios(List<SegUsuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}
<<<<<<< HEAD
	public int getIdSegUsuarioSeleccionado() {
		return idSegUsuarioSeleccionado;
	}
	public void setIdSegUsuarioSeleccionado(int idSegUsuarioSeleccionado) {
		this.idSegUsuarioSeleccionado = idSegUsuarioSeleccionado;
=======
	
	public int getIdSegUsarioSeleccionado() {
		return idSegUsarioSeleccionado;
	}
	public void setIdSegUsarioSeleccionado(int idSegUsarioSeleccionado) {
		this.idSegUsarioSeleccionado = idSegUsarioSeleccionado;
>>>>>>> 727c903af50055b24c6b3aa8b95b773b94b16352
	}
	public PryProyecto getProyectoSeleccionado() {
		return proyectoSeleccionado;
	}
	public void setProyectoSeleccionado(PryProyecto proyectoSeleccionado) {
		this.proyectoSeleccionado = proyectoSeleccionado;
	}
	
	
<<<<<<< HEAD
=======
	

>>>>>>> 727c903af50055b24c6b3aa8b95b773b94b16352
}
