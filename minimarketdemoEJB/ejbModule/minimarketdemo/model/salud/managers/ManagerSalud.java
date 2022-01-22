package minimarketdemo.model.salud.managers;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import minimarketdemo.model.core.entities.SalCarnet;

/**
 * Session Bean implementation class ManagerSalud
 */
@Stateless
@LocalBean
public class ManagerSalud {
@PersistenceContext
private EntityManager eManager;
    /**
     * Default constructor. 
     */
    public ManagerSalud() {
        
    }
//Metodos para la secretaria
    public void insertarCarnet(SalCarnet carnet) {
    	//Para que se inicialse en la vista y bd
    	carnet.setVerificado(false);
    	eManager.persist(carnet);
    }
    //Metodo para mostrar todos los carnets
    public List<SalCarnet> findAllCarnet(){
    	return eManager.createNamedQuery("SalCarnet.findAll", SalCarnet.class).getResultList();
    }
//Metodos para la enfermera
    //La enfermera quiere consultar todos los datos,pero con verificar
    public void validarCarnet(int idSalCarnet) {
    	//Le busco en la db
    	SalCarnet carnet=eManager.find(SalCarnet.class, idSalCarnet);
    	carnet.setVerificado(true); 
    	eManager.merge(carnet); // este actualiza
    	
    }
}

