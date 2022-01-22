package minimarketdemo.model.alquiler.managers;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import minimarketdemo.model.core.entities.AlqProducto;
import minimarketdemo.model.core.entities.PryProyecto;
import minimarketdemo.model.core.entities.PryTarea;
import minimarketdemo.model.core.entities.AlqProducto ;
import minimarketdemo.model.core.entities.SegUsuario;
import minimarketdemo.model.core.managers.ManagerDAO;

/**
 * Session Bean implementation class ManagerAlquiler
 */
@Stateless
@LocalBean
public class ManagerAlquiler {
	@PersistenceContext
	private EntityManager eManager;
	@EJB
	private ManagerDAO mDAO;
    public ManagerAlquiler() {

    }
    public void insertarProducto(AlqProducto producto) {
    	//Para que se inicialse en la vista y bd
    	//producto.setVerificado(false);
    	eManager.persist(producto);
    }
    //Metodo para mostrar todos los carnets
    public List<AlqProducto > findAllAlqProducto(){
    	return eManager.createNamedQuery("AlqProducto.findAll", AlqProducto.class).getResultList();
    }
//Metodos para la enfermera
    //La enfermera quiere consultar todos los datos,pero con verificar
    public void validarCarnet(int idAlqProducto ) {
    	//Le busco en la db
    	AlqProducto  producto=eManager.find(AlqProducto .class, idAlqProducto );
    	//carnet.setVerificado(true); 
    	eManager.merge(producto); // este actualiza
    	
    }

}
