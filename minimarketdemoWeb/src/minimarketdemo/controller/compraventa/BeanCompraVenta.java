package minimarketdemo.controller.compraventa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;

import minimarketdemo.controller.JSFUtil;
import minimarketdemo.model.auditoria.managers.ManagerAuditoria;
import minimarketdemo.model.compras.managers.ManagerEmpleado;
import minimarketdemo.model.core.entities.AudBitacora;
import minimarketdemo.model.core.entities.Cabecera;
import minimarketdemo.model.core.entities.Ingreso;
import minimarketdemo.model.core.entities.Persona;
import minimarketdemo.model.core.entities.Producto;
import minimarketdemo.model.core.entities.Proveedore;
import minimarketdemo.model.core.entities.Salida;
import minimarketdemo.model.core.entities.Stock;
import minimarketdemo.model.core.utils.ModelUtil;

@Named
@SessionScoped
public class BeanCompraVenta implements Serializable {
	private static final long serialVersionUID = 1L;
	//Compra
			@EJB
	private ManagerEmpleado managerEmpleado;
	private List<Producto> listaProductos;
	private List<Proveedore> listaProveedores;
	private Producto ProductoCompra;
	private int Cantidad;
	private Proveedore Proveedor;
	private Persona personaProv;
	private List<Ingreso> CarritoDeCompras;
	private Ingreso NuevoIngreso;
	private int PrecioUnitario;
	private Producto NewProducto;
	private int CategoriaID;
	private Stock NewStock;
	private List<Ingreso> IngresosTotales;
	private Cabecera CabeceraSeleccionada;
	private List<Cabecera> ListaCabecerasIngresos;
	private List<Cabecera> ListaCabecerasSalidas;
	
	public BeanCompraVenta() {

	}

	@PostConstruct
	public void inicializacion() {
		listaProveedores=new ArrayList<Proveedore>();
		CarritoDeCompras=new ArrayList<Ingreso>();
		NuevoIngreso=new Ingreso();
		personaProv=new Persona();
		personaProv.setCedula("XXXXXXXXXXXXXX");
		personaProv.setNombre("XXXXXXX XXXXXXX");
		personaCli=new Persona();
		personaCli.setCedula("XXXXXXXXXXXXXX");
		personaCli.setNombre("XXXXXXX XXXXXXX");
		NewProducto=new Producto();
		NewStock=new Stock();
		IngresosTotales=new ArrayList<Ingreso>();
		CarritoDeVentas=new ArrayList<Salida>();
		this.Proveedor=new Proveedore();
		this.CabeceraSeleccionada=new Cabecera();
		ListaCabecerasIngresos=new ArrayList<Cabecera>();
		ListaCabecerasSalidas=new ArrayList<Cabecera>();
	}
	
		
		public List<Producto> findAllProductos() {
			return managerEmpleado.findAllProductos();
		}
		public List<Proveedore> findAllProveedores(){
			return managerEmpleado.findAllProveedores();
		}
		public Persona getPersonaInfo(Proveedore prov) {
			return managerEmpleado.findPersonaByIdProveedor(prov.getPersona().getIdPersona());
		}
		public Stock getStockInfo(Producto prod) {
			return managerEmpleado.findStockByIdProducto(prod.getIdProducto());
		}
		public void SetProductoCompra(Producto prod) {
			this.Cantidad=0;
			this.PrecioUnitario=0;
			this.ProductoCompra=prod;
		}
		public List<Producto> getListaProductos() {
			if(this.listaProductos==null) {
				this.listaProductos=managerEmpleado.findAllProductos();
				for(int i=0;i<listaProductos.size();i++) {
					if(listaProductos.get(i).getEstado()==false) {
						listaProductos.remove(i);
					}
				}
			}
			return this.listaProductos;
		}
		
		public void findProveedorByCedula() {
			if(managerEmpleado.findProveedorByCedula(personaProv.getCedula())!=null) {
				this.Proveedor=managerEmpleado.findProveedorByCedula(personaProv.getCedula());
				this.personaProv=managerEmpleado.findPersonaByCedula(personaProv.getCedula());
			}
		}
		public void AñadirAlCarrito() {
			if(Proveedor!=null && Cantidad + this.getStockInfo(ProductoCompra).getCantidad()<=this.getStockInfo(ProductoCompra).getStockMaximo()) {
				managerEmpleado.AñadirAlCarritoDeCompras(Cantidad, PrecioUnitario, ProductoCompra, Proveedor, CarritoDeCompras, listaProductos);
				
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", "Producto añadido satisfactoriamente");

		        PrimeFaces.current().dialog().showMessageDynamic(message);
			}else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", "No se pudo añadir el producto al carrito");

		        PrimeFaces.current().dialog().showMessageDynamic(message);
			}
		}
		
		public void InsertarProveedor() throws Exception {
			managerEmpleado.IngresarProveedor(personaProv, Proveedor);
			if(managerEmpleado.findProveedorByCedula(personaProv.getCedula())!=null) {
				this.Proveedor=managerEmpleado.findProveedorByCedula(personaProv.getCedula());
				this.personaProv=managerEmpleado.findPersonaByCedula(personaProv.getCedula());
			}
		}
		public String IrAlCarrito() {
			return "Carritodecompras.xhtml";
		}
		public void EliminarDelCarrito(Ingreso Ing) {
			this.listaProductos.add(Ing.getProducto());
			this.CarritoDeCompras.remove(Ing);
		}
		public void EliminarDelCarritoVentas(Salida sal) {
			this.listaProductos.add(sal.getProducto());
			this.CarritoDeVentas.remove(sal);
		}
		public String actionRegresarAcomprar() {
			return "compras.xhtml";
		}
		public void EstablecerCabeceraCompra(Cabecera Cab) {
			this.CabeceraSeleccionada=Cab;
		}
		public void EstablecerCabeceraSalida(Cabecera Cab) {
			this.CabeceraSeleccionada=Cab;
		}
		public void actionFinalizarCompra() throws Exception {
			managerEmpleado.IngresarTransaccionCompra(CarritoDeCompras);
			this.listaProductos=managerEmpleado.findAllProductos();
			CarritoDeCompras=new ArrayList<Ingreso>();
			NuevoIngreso=new Ingreso();
			personaProv=new Persona();
			personaProv.setCedula("XXXXXXXXXXXXXX");
			personaProv.setNombre("XXXXXXX XXXXXXX");
			personaCli=new Persona();
			personaCli.setCedula("XXXXXXXXXXXXXX");
			personaCli.setNombre("XXXXXXX XXXXXXX");
		}
		public void actionFinalizarVenta() throws Exception {
			managerEmpleado.IngresarTransaccionVenta(CarritoDeVentas);
			this.listaProductos=managerEmpleado.findAllProductos();
			CarritoDeVentas=new ArrayList<Salida>();
			NuevoIngreso=new Ingreso();
			personaProv=new Persona();
			personaProv.setCedula("XXXXXXXXXXXXXX");
			personaProv.setNombre("XXXXXXX XXXXXXX");
			personaCli=new Persona();
			personaCli.setCedula("XXXXXXXXXXXXXX");
			personaCli.setNombre("XXXXXXX XXXXXXX");
		}
		public int actionCalcularValorFinal(Ingreso Ing) {
			return Integer.parseInt(Ing.getCantidadIngresos().multiply(Ing.getPrecioCompra())+"");
		}
		public int actionCalcularValorFinal(Salida Sal) {
			return Integer.parseInt(Sal.getCantidadSalida().multiply(Sal.getPrecioVenta())+"");
		}
		public void InsertarNuevoProducto() throws Exception {
			NewProducto.setCategoria(managerEmpleado.findCategoriaByID(CategoriaID));
			Producto TmpProd=new Producto();
			TmpProd.setCategoria(NewProducto.getCategoria());
			TmpProd.setCodProducto(NewProducto.getCodProducto());
			TmpProd.setDescripcion(NewProducto.getDescripcion());
			TmpProd.setEstado(true);
			TmpProd.setNombreProducto(NewProducto.getNombreProducto());
			TmpProd.setUnidaMedida(NewProducto.getUnidaMedida());
			Stock TmpStock=new Stock();
			TmpStock.setProducto(TmpProd);
			TmpStock.setCantidad(NewStock.getStockMinimo());
			TmpStock.setStockMaximo(NewStock.getStockMaximo());
			TmpStock.setStockMinimo(NewStock.getStockMinimo());
			managerEmpleado.RegistrarNuevoProducto(TmpProd);
			managerEmpleado.RegistrarNuevoStock(TmpStock);
			this.listaProductos.add(TmpProd);
		}
		public void actionFindCabeceraByID() {
			CabeceraSeleccionada=managerEmpleado.findCabeceraByID(CabeceraSeleccionada.getIdCabecera());
		}
		public void setListaProductos(List<Producto> listaProductos) {
			this.listaProductos = listaProductos;
		}

		public List<Proveedore> getListaProveedores() {
			return managerEmpleado.findAllProveedores();
		}

		public void setListaProveedores(List<Proveedore> listaProveedores) {
			this.listaProveedores = listaProveedores;
		}

		public Producto getProductoCompra() {
			return ProductoCompra;
		}

		public void setProductoCompra(Producto productoCompra) {
			ProductoCompra = productoCompra;
		}

		public int getCantidad() {
			return Cantidad;
		}

		public void setCantidad(int cantidad) {
			Cantidad = cantidad;
		}

		public List<Ingreso> getCarritoDeCompras() {
			return CarritoDeCompras;
		}

		public void setCarritoDeCompras(List<Ingreso> carritoDeCompras) {
			CarritoDeCompras = carritoDeCompras;
		}

		public Ingreso getNuevoIngreso() {
			return NuevoIngreso;
		}

		public void setNuevoIngreso(Ingreso nuevoIngreso) {
			NuevoIngreso = nuevoIngreso;
		}

		public Proveedore getProveedor() {
			return Proveedor;
		}

		public void setProveedor(Proveedore proveedor) {
			Proveedor = proveedor;
		}

		public Persona getPersonaProv() {
			return personaProv;
		}

		public void setPersonaProv(Persona personaProv) {
			this.personaProv = personaProv;
		}

		public int getPrecioUnitario() {
			return PrecioUnitario;
		}

		public void setPrecioUnitario(int precioUnitario) {
			PrecioUnitario = precioUnitario;
		}

		public Producto getNewProducto() {
			return NewProducto;
		}

		public void setNewProducto(Producto newProducto) {
			NewProducto = newProducto;
		}

		public int getCategoriaID() {
			return CategoriaID;
		}

		public void setCategoriaID(int categoriaID) {
			CategoriaID = categoriaID;
		}

		public Stock getNewStock() {
			return NewStock;
		}

		public void setNewStock(Stock newStock) {
			NewStock = newStock;
		}

		public List<Ingreso> getIngresosTotales() {
			return managerEmpleado.findAllIngresosByCabecera(CabeceraSeleccionada);
		}

		public void setIngresosTotales(List<Ingreso> ingresosTotales) {
			IngresosTotales = ingresosTotales;
		}
		
		public Cabecera getCabeceraSeleccionada() {
			return CabeceraSeleccionada;
		}

		public void setCabeceraSeleccionada(Cabecera cabeceraSeleccionada) {
			CabeceraSeleccionada = cabeceraSeleccionada;
		}
		//Venta
		private Persona personaCli;
		private List<Salida> CarritoDeVentas;
		private List<Salida> SalidasTotales;
		public String IrAlCarritoVentas() {
			return "Carritodeventas.xhtml";
		}
		public void InsertarCliente() throws Exception {
			managerEmpleado.IngresarCliente(personaCli);
		}
		public void findClienteByCedula() {
			if(managerEmpleado.findPersonaByCedula(personaCli.getCedula())!=null) {
				this.personaCli=managerEmpleado.findPersonaByCedula(personaCli.getCedula());
			}
		}
		public void AñadirAlCarritoVenta() {
			if(managerEmpleado.findPersonaByCedula(personaCli.getCedula())!=null && this.getStockInfo(ProductoCompra).getCantidad()-Cantidad>=this.getStockInfo(ProductoCompra).getStockMinimo()) {
				managerEmpleado.AñadirAlCarritoDeVentas(Cantidad, PrecioUnitario, ProductoCompra, personaCli, CarritoDeVentas, listaProductos);
				
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", "Producto añadido satisfactoriamente");

		        PrimeFaces.current().dialog().showMessageDynamic(message);
			}else {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Message", "No se pudo añadir el producto al carrito");

		        PrimeFaces.current().dialog().showMessageDynamic(message);
			}
		}
		public String actionRegresarAvender() {
			return "ventas.xhtml";
		}
		public Persona getPersonaCli() {
			return personaCli;
		}

		public void setPersonaCli(Persona personaCli) {
			this.personaCli = personaCli;
		}

		public List<Salida> getCarritoDeVentas() {
			return CarritoDeVentas;
		}

		public void setCarritoDeVentas(List<Salida> carritoDeVentas) {
			CarritoDeVentas = carritoDeVentas;
		}

		public List<Salida> getSalidasTotales() {
			return managerEmpleado.findAllSalidas();
		}

		public void setSalidasTotales(List<Salida> salidasTotales) {
			SalidasTotales = salidasTotales;
		}

		public List<Cabecera> getListaCabeceras() {
			return managerEmpleado.findAllCabeceras();
		}

		public List<Cabecera> getListaCabecerasIngresos() {
			return managerEmpleado.findAllCabecerasIngresos();
		}

		public void setListaCabecerasIngresos(List<Cabecera> listaCabecerasIngresos) {
			ListaCabecerasIngresos = listaCabecerasIngresos;
		}

		public List<Cabecera> getListaCabecerasSalidas() {
			return managerEmpleado.findAllCabecerasSalidas();
		}

		public void setListaCabecerasSalidas(List<Cabecera> listaCabecerasSalidas) {
			ListaCabecerasSalidas = listaCabecerasSalidas;
		}
		
}