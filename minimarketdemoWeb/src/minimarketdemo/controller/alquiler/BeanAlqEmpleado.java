package minimarketdemo.controller.alquiler;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.alquiler.managers.ManagerAlquiler;
import minimarketdemo.model.core.entities.AlqProducto;
import minimarketdemo.model.core.entities.SalCarnet;
import minimarketdemo.model.proyectos.managers.ManagerProyectos;
import minimarketdemo.model.salud.managers.ManagerSalud;

@Named
@SessionScoped
public class BeanAlqEmpleado implements Serializable {
	@EJB
	private ManagerAlquiler mAlquiler;

private  AlqProducto productoNuevo;
private List<AlqProducto> listaproducto;
	public BeanAlqEmpleado() {
		// TODO Auto-generated constructor stub
	}
	@PostConstruct
	public void inicializar() {
		productoNuevo= new AlqProducto();
		//Cada vez que se inserte sale la lista
		listaproducto=mAlquiler.findAllAlqProducto();
	}

	public void actionListenerInsertarCarnet() {
		mAlquiler.insertarProducto(productoNuevo);
		productoNuevo=new AlqProducto(); // para vaciar los campos
		listaproducto=mAlquiler.findAllAlqProducto();
		JSFUtil.crearMensajeINFO("Producto guardado");
	}
	public AlqProducto getProductoNuevo() {
		return productoNuevo;
	}
	public void setProductoNuevo(AlqProducto productoNuevo) {
		this.productoNuevo = productoNuevo;
	}
	public List<AlqProducto> getListaproducto() {
		return listaproducto;
	}
	public void setListaproducto(List<AlqProducto> listaproducto) {
		this.listaproducto = listaproducto;
	}
	

}
