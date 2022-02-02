package dunamis.model.managers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import minimarketdemo.model.core.entities.Persona;
import minimarketdemo.model.core.entities.Producto;
import minimarketdemo.model.core.entities.ProformaDet;
import minimarketdemo.model.core.entities.ProformasCab;
import minimarketdemo.model.core.managers.ManagerDAO;



/**
 * Session Bean implementation class ManagerProyectos
 */
@Stateless
@LocalBean
public class ManagerCompras {

	@PersistenceContext
	private EntityManager em;

	@EJB
	private ManagerDAO mDAO;

	public ManagerCompras() {
	}

	// Listar Productos
	public List<Producto> findAllProductos() {
		return mDAO.findAll(Producto.class);
	}

	// Listar Personaes
	public List<Persona> findAllPersonas() {
		return mDAO.findAll(Persona.class);
	}

	// Listar Compras
	public List<ProformasCab> findAllProformasCabs() {
		return mDAO.findAll(ProformasCab.class);
	}

	// Listar detalles segun proforma
	public List<ProformaDet> findDetalleByCompra(int proformaId) {
		return mDAO.findWhere(ProformaDet.class, "o.proformasCab.pfCabId=" + proformaId, "o.pfDetId");
		//return mDAO.findWhere(ProformaDet.class, "o.proformasCab.pfCabId="+proformaId, "o.pfDetId");
	}

	// ---------------------COMPRAS MULTIPLE--------------------------------------
	public ProformaDet crearDetalleCompra1(int idProduct, int cantidadIngreso, double precioIngreso) throws Exception {
		Producto producto = (Producto) mDAO.findById(Producto.class, idProduct);

		ProformaDet nuevoDetalle = new ProformaDet();
		nuevoDetalle.setProducto(producto);
		nuevoDetalle.setPfDetCantidad(cantidadIngreso);
		nuevoDetalle.setPfDetPrecio(new BigDecimal(precioIngreso));
		nuevoDetalle.setPfDetPreciototal( new BigDecimal(cantidadIngreso* precioIngreso));
		

		return nuevoDetalle;
	}

	public double totalDetalleCompra(List<ProformaDet> listaDetalleCompra) {
		double total = 0;
		for (ProformaDet d : listaDetalleCompra)
			total += d.getPfDetPreciototal().doubleValue();
		return total;
	}

	public void registrarCompra1(List<ProformaDet> listaDetalleCompra	,int personaIngreso, Date fecha,
			double iva, double subtotal ) throws Exception {

		Persona persona = em.find(Persona.class, personaIngreso);

		ProformasCab proformaCab = new ProformasCab();
		proformaCab.setPfCabFecha(fecha);
		proformaCab.setPersona(persona);
		
		proformaCab.setPfCabIva(new BigDecimal(iva));
		proformaCab.setPfCabSubtototal(new BigDecimal(subtotal));
		proformaCab.setPfCabTotal(new BigDecimal(totalDetalleCompra(listaDetalleCompra)));

		List<ProformaDet> listaDetalle = new ArrayList<ProformaDet>();
		proformaCab.setProformaDets(listaDetalle);

		for (ProformaDet det : listaDetalleCompra) {
			ProformaDet detalle = new ProformaDet();
			detalle.setProformasCabs(proformaCab);
			Producto producto = (Producto) mDAO.findById(Producto.class, det.getProducto().getIdProducto());
			detalle.setProducto(producto);
			detalle.setPfDetCantidad(det.getPfDetCantidad());
			detalle.setPfDetPrecio(det.getPfDetPrecio());
			detalle.setPfDetPreciototal(det.getPfDetPreciototal());
			listaDetalle.add(detalle);
//			
//		
		}

		em.persist(proformaCab);
	}

	// ------------------COMPRA/DETALLE-------------------------------------

	// Detalles
	public ProformasCab crearDetalleCompra2(ProformasCab nuevaProformaCab, int productoIngreso, int cantidadIngreso, double precioIngreso)
			throws Exception {

		if (nuevaProformaCab == null) {
			nuevaProformaCab = new ProformasCab();
			nuevaProformaCab.setProformaDets(new ArrayList<ProformaDet>());
			nuevaProformaCab.setPfCabFecha(new Date());
			nuevaProformaCab.setPfCabSubtototal(new BigDecimal(0));
			nuevaProformaCab.setPfCabIva(new BigDecimal(0));
			nuevaProformaCab.setPfCabTotal(new BigDecimal(0));
			
		}

		Producto producto = (Producto) mDAO.findById(Producto.class, productoIngreso);

		ProformaDet nuevoDetalle = new ProformaDet();
		nuevoDetalle.setProducto(producto);
		nuevoDetalle.setPfDetCantidad(cantidadIngreso);
		nuevoDetalle.setPfDetPrecio(new BigDecimal(precioIngreso));
		nuevoDetalle.setPfDetPreciototal(new BigDecimal(cantidadIngreso * precioIngreso).setScale(2, RoundingMode.HALF_UP));
		nuevoDetalle.setProformasCabs(nuevaProformaCab);
		nuevaProformaCab.getProformaDets().add(nuevoDetalle);
		return nuevaProformaCab;
		
		
	}

	// Cabecera
//	public void registrarCompra2(ProformasCab nuevaproformaCab,  int personaIngreso, Date fechaIngreso,
//			 double subtotal, double iva, double totalLista) throws Exception {
//
//		if (nuevaproformaCab == null || nuevaproformaCab.getProformaDets() == null
//				|| nuevaproformaCab.getProformaDets().size() == 0)
//			throw new Exception("Debe seleccionar al menos un Producto");
//
//		Persona persona = (Persona) mDAO.findById(Persona.class, personaIngreso);
//
//		
//		nuevaproformaCab.setPersona(persona);
//		nuevaproformaCab.setPfCabFecha(fechaIngreso);
//		nuevaproformaCab.setPfCabSubtototal(new BigDecimal(subtotal));
//		
//		nuevaproformaCab.setPfCabIva(new BigDecimal(iva));
//
//		double ivaT = totalLista * (iva * 0.01);
//		double total = totalLista + ivaT;
//		nuevaproformaCab.setPfCabTotal(new BigDecimal(total).setScale(2, RoundingMode.HALF_UP)); 
//
//		// Actualizar productos
//		for (int i = 0; i < nuevaproformaCab.getProformaDets().size(); i++) {
//			int idProducto = nuevaproformaCab.getProformaDets().get(i).getProducto().getIdProducto();
//			Producto producto = (Producto) mDAO.findById(Producto.class, idProducto);
//
//			int cantidad = nuevaproformaCab.getProformaDets().get(i).getPfDetCantidad().intValue();
////			int stock = producto.getStock().intValue();
////			producto.setStock(new BigDecimal(stock + cantidad));
//
//			double precio = nuevaproformaCab.getProformaDets() .get(i).getPfDetPrecio().doubleValue();
//			//double ganancia = precio + (precio * 0.01);
//			//producto.setPrecioDistribuidor(new BigDecimal(precio));
//			//producto.setPrecioUnitario(new BigDecimal(ganancia));
//
//			mDAO.actualizar(producto);
//		}
//
//		mDAO.insertar(nuevaproformaCab);
//	}

	public double totalLista(ProformasCab comprita) {
		List<ProformaDet> listaDetalleCompra = comprita.getProformaDets();
		double total = 0;
		for (ProformaDet d : listaDetalleCompra)
			total += d.getPfDetPreciototal().doubleValue();
		return total;
	}

//	public int calcularStockInicial(int productoSeleccionado) throws Exception {
//		Producto producto = (Producto) mDAO.findById(Producto.class, productoSeleccionado);
//		return producto.getStock().intValue();
//	}

	public BigDecimal calculoPrecioTotal(BigDecimal precioU, int cant) {
		double precio_unitario = precioU.doubleValue();
		double precioTotal = precio_unitario * cant;
		BigDecimal precioT = new BigDecimal(precioTotal);
		return precioT;
	}

//	public void actualizarProducto(Producto producto, int cantidad, double valorUnitario) throws Exception {
//
//		int stock = producto.getStock().intValue();
//		stock = stock + cantidad;
//		producto.setStock(new BigDecimal(stock));
//
//		double nuevoPrecio = (valorUnitario + ganancia);
//		producto.setPrecioDistribuidor(new BigDecimal(valorUnitario));
//		producto.setPrecioUnitario(new BigDecimal(nuevoPrecio));
//		mDAO.actualizar(producto);
//	}
	// Borrar de ProformasCab
		public void eliminarProformasCab1(int ProformasCabId) throws Exception {
			ProformasCab proformasCab = (ProformasCab) mDAO.findById(ProformasCab.class, ProformasCabId); // Encontrar el
																											// cliente a
//				BigDecimal sbt	= new BigDecimal(0)	;																					// eliminar
			//if (proformasCab.getPfCabSubtototal().);
			//throw new Exception("No se puede elimininar la proforma porque tiene productos registrados.");
			mDAO.eliminar(ProformasCab.class, proformasCab.getPfCabId());
			// TODO agregar uso de LoginDTO para auditar metodo.
		}
}
