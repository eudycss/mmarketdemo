package minimarketdemo.model.compras.managers;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import minimarketdemo.model.core.entities.AudBitacora;
import minimarketdemo.model.core.entities.Cabecera;
import minimarketdemo.model.core.entities.Categoria;
import minimarketdemo.model.core.entities.Ingreso;
import minimarketdemo.model.core.entities.Persona;
import minimarketdemo.model.core.entities.Producto;
import minimarketdemo.model.core.entities.Proveedore;
import minimarketdemo.model.core.entities.PryTarea;
import minimarketdemo.model.core.entities.Salida;
import minimarketdemo.model.core.entities.Stock;
import minimarketdemo.model.core.managers.ManagerDAO;
import minimarketdemo.model.seguridades.dtos.LoginDTO;

/**
 * Session Bean implementation class ManagerAuditoria
 */
@Stateless
@LocalBean
public class ManagerEmpleado {
	@PersistenceContext
	private EntityManager em;
	@EJB
	private ManagerDAO mDAO;
    /**
     * Default constructor. 
     */
    public ManagerEmpleado() {
        
    }
    public List<Producto> findAllProductos() {
    	List<Producto> Productos=mDAO.findWhere(Producto.class, "estado=true", null);
    	if(Productos==null) {
    		return new ArrayList<Producto>();
    	}
    	return Productos;
    }
    public List<Persona> findAllPersona(){
    	List<Persona> Personas=mDAO.findWhere(Persona.class, "estado_persona=true", null);
    			if(Personas==null) {
    				return new ArrayList<Persona>();
    			}
    	return Personas;
    }
    public List<Proveedore> findAllProveedores(){
    	List<Proveedore> Proveedores=mDAO.findWhere(Proveedore.class, "estado=true", null);
    	return mDAO.findAll(Proveedore.class);
    }
    public Persona findPersonaByIdProveedor(int IdPersonaProveedor) {
    	return (Persona)em.createQuery("select o from "+Persona.class.getSimpleName()+" o where id_persona = "+IdPersonaProveedor+" and estado_persona=true").getResultList().get(0);
    }
    public Stock findStockByIdProducto(int IdProducto) {
    	return (Stock)em.createQuery("select o from "+Stock.class.getSimpleName()+" o where id_producto = "+IdProducto).getResultList().get(0);
    }
    public Proveedore findProveedorByCedula(String Cedula) {
    	try {
    		Persona Personatmp=(Persona)em.createQuery("select o from "+Persona.class.getSimpleName()+" o where cedula = '"+ Cedula+"' and estado_persona=true").getResultList().get(0);
    		return (Proveedore)em.createQuery("select o from "+Proveedore.class.getSimpleName() + " o where id_persona = " + Personatmp.getIdPersona()).getResultList().get(0);
    	}catch(Exception e){
    		return null;
    	}
    }
    public Cabecera findCabeceraByID(int CabID) {
    	return (Cabecera)mDAO.findWhere(Cabecera.class, "id_cabecera = "+CabID+" and estado_cabecera=true", null);
    }
    public List<Cabecera> findAllCabeceras(){
    	if(mDAO.findWhere(Cabecera.class, "estado_cabecera=true", null)==null) {
    		return new ArrayList<Cabecera>();
    	}
    	return mDAO.findWhere(Cabecera.class, "estado_cabecera=true", null);
    }
    public Persona findPersonaByCedula(String Cedula) {
    	try {
    		return (Persona)em.createQuery("select o from "+Persona.class.getSimpleName()+" o where cedula = '"+ Cedula+"' and estado_persona=true").getResultList().get(0);
    	}catch(Exception e){
    		return null;
    	}
    }
    public void IngresarProveedor(Persona Identidad,Proveedore Proveedor) throws Exception {
    	Persona PersonaID=new Persona();
    	PersonaID.setNombre(Identidad.getNombre());
    	PersonaID.setApellido(Identidad.getApellido());
    	PersonaID.setCedula(Identidad.getCedula());
    	PersonaID.setCorreo(Identidad.getCorreo());
    	PersonaID.setDireccion(Identidad.getDireccion());
    	PersonaID.setTelefono(Identidad.getTelefono());
    	PersonaID.setEstadoPersona(true);
    	mDAO.insertar(PersonaID);
    	Proveedore ProveedorID = new Proveedore();
    	ProveedorID.setMarcaProv(Proveedor.getMarcaProv());
    	ProveedorID.setDescripcion(Proveedor.getDescripcion());
    	ProveedorID.setEstado(true);
    	ProveedorID.setPersona(PersonaID);
    	mDAO.insertar(ProveedorID);
    }
    public void AñadirAlCarritoDeCompras(int Cantidad,int PrecioUnitario,Producto ProductoCompra,Proveedore Proveedor, List<Ingreso> CarritoDeCompras,List<Producto> listaProductos) {
    	Ingreso NuevoIngreso=new Ingreso();
		NuevoIngreso.setCantidadIngresos(BigDecimal.valueOf(Cantidad));
		NuevoIngreso.setEstadoIngreso(true);
		
		NuevoIngreso.setPrecioCompra(BigDecimal.valueOf(PrecioUnitario));
		NuevoIngreso.setProducto(ProductoCompra);
		NuevoIngreso.setProveedore(Proveedor);
		CarritoDeCompras.add(NuevoIngreso);
		listaProductos.remove(ProductoCompra);
    }
    public void AñadirAlCarritoDeVentas(int Cantidad,int PrecioUnitario,Producto ProductoVenta,Persona Cliente, List<Salida> CarritoDeVentas,List<Producto> listaProductos) {
    	Salida NuevaSalida=new Salida();
    	NuevaSalida.setCantidadSalida(BigDecimal.valueOf(Cantidad));
    	NuevaSalida.setEstadoSalida(true);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, LocalDateTime.now().getYear());
		calendar.set(Calendar.DAY_OF_MONTH, LocalDateTime.now().getDayOfMonth());
		calendar.set(Calendar.MONTH, LocalDateTime.now().getMonthValue()-1);
		NuevaSalida.setPrecioVenta(BigDecimal.valueOf(PrecioUnitario));
		NuevaSalida.setProducto(ProductoVenta);
		NuevaSalida.setPersona(Cliente);
		CarritoDeVentas.add(NuevaSalida);
		listaProductos.remove(ProductoVenta);
    }
    public void IngresarTransaccionCompra(List<Ingreso> CarritoDeCompras) throws Exception {
    	Stock StockTmp;
    	Cabecera CabeceraNueva = new Cabecera();
    	Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, LocalDateTime.now().getYear());
		calendar.set(Calendar.DAY_OF_MONTH, LocalDateTime.now().getDayOfMonth());
		calendar.set(Calendar.MONTH, LocalDateTime.now().getMonth().getValue()-1);
    	java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
    	CabeceraNueva.setFechaTransaccion(date);
    	CabeceraNueva.setEstadoCabecera(true);
    	CabeceraNueva.setTotalTransaccion(BigDecimal.valueOf(0));
    	mDAO.insertar(CabeceraNueva);
    	CabeceraNueva=(Cabecera) mDAO.findAll(Cabecera.class).get(mDAO.findAll(Cabecera.class).size()-1);
    	for(int i=0;i<CarritoDeCompras.size();i++) {
    		CarritoDeCompras.get(i).setCabecera(CabeceraNueva);
    		mDAO.insertar(CarritoDeCompras.get(i));
    	}
    	for(int i=0;i<CarritoDeCompras.size();i++) {
    		StockTmp=this.findStockByIdProducto(CarritoDeCompras.get(i).getProducto().getIdProducto());
    		StockTmp.setCantidad(Integer.parseInt(CarritoDeCompras.get(i).getCantidadIngresos().add(BigDecimal.valueOf(StockTmp.getCantidad()))+""));
    		CabeceraNueva.setTotalTransaccion(BigDecimal.valueOf(CabeceraNueva.getTotalTransaccion().doubleValue()+(CarritoDeCompras.get(i).getCantidadIngresos().doubleValue()*CarritoDeCompras.get(i).getPrecioCompra().doubleValue())));
    		mDAO.actualizar(StockTmp);
    	}
    }
    public void IngresarTransaccionVenta(List<Salida> CarritoDeVentas) throws Exception {
    	Stock StockTmp;
    	Cabecera CabeceraNueva = new Cabecera();
    	Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, LocalDateTime.now().getYear());
		calendar.set(Calendar.DAY_OF_MONTH, LocalDateTime.now().getDayOfMonth());
		calendar.set(Calendar.MONTH, LocalDateTime.now().getMonth().getValue()-1);
    	java.sql.Date date = new java.sql.Date(calendar.getTime().getTime());
    	CabeceraNueva.setFechaTransaccion(date);
    	CabeceraNueva.setEstadoCabecera(true);
    	CabeceraNueva.setTotalTransaccion(BigDecimal.valueOf(0));
    	mDAO.insertar(CabeceraNueva);
    	CabeceraNueva=(Cabecera) mDAO.findAll(Cabecera.class).get(mDAO.findAll(Cabecera.class).size()-1);
    	for(int i=0;i<CarritoDeVentas.size();i++) {
    		CarritoDeVentas.get(i).setCabecera(CabeceraNueva);
    		mDAO.insertar(CarritoDeVentas.get(i));
    	}
    	for(int i=0;i<CarritoDeVentas.size();i++) {
    		StockTmp=this.findStockByIdProducto(CarritoDeVentas.get(i).getProducto().getIdProducto());
    		StockTmp.setCantidad(Integer.parseInt((BigDecimal.valueOf(StockTmp.getCantidad()).subtract(CarritoDeVentas.get(i).getCantidadSalida()) )+""));
    		CabeceraNueva.setTotalTransaccion(BigDecimal.valueOf(CabeceraNueva.getTotalTransaccion().doubleValue()+(CarritoDeVentas.get(i).getCantidadSalida().doubleValue()*CarritoDeVentas.get(i).getPrecioVenta().doubleValue())));
    		mDAO.actualizar(StockTmp);
    	}
    }
    public void RegistrarNuevoProducto(Producto NewProd) throws Exception {
    	mDAO.insertar(NewProd);
    }
    public Categoria findCategoriaByID(int ID) throws Exception {
    	return (Categoria) mDAO.findById(Categoria.class, ID);
    }
    public void RegistrarNuevoStock(Stock StockNew) throws Exception {
    	mDAO.insertar(StockNew);
    }
    public List<Cabecera> findAllCabecerasIngresos() {
    	//Paso todos los ingresos existentes
    	List<Cabecera> CabTmp=mDAO.findWhere(Cabecera.class,"estado_cabecera=true", null);
    	//Filtro solo los accesos de tipo ingreso
    	List<Cabecera> CabTmp2=new ArrayList<Cabecera>();
    	for(int i=0;i<CabTmp.size();i++) {
    		if(CabTmp.get(i).getIngresos().size()!=0) {
    			CabTmp2.add(CabTmp.get(i));
    		}
    	}
    	return CabTmp2;
    }
    public List<Ingreso> findAllIngresosByCabecera(Cabecera Cab){
    	return mDAO.findWhere(Ingreso.class, "id_cabecera="+Cab.getIdCabecera()+" and estado_ingreso=true", null);
    }
    public List<Cabecera> findAllCabecerasSalidas() {
    	//Paso todos los ingresos existentes
    	List<Cabecera> CabTmp=mDAO.findWhere(Cabecera.class,"estado_cabecera=true", null);
    	//Filtro solo los accesos de tipo ingreso
    	List<Cabecera> CabTmp2=new ArrayList<Cabecera>();
    	for(int i=0;i<CabTmp.size();i++) {
    		if(CabTmp.get(i).getSalidas().size()!=0) {
    			CabTmp2.add(CabTmp.get(i));
    		}
    	}
    	return CabTmp2;
    }
    public List<Salida> findAllSalidasByCabecera(Cabecera Cab){
    	return mDAO.findWhere(Salida.class, "id_cabecera="+Cab.getIdCabecera()+" and estado_salida=true", null);
    }
    public void IngresarCliente(Persona Identidad) throws Exception {
    	Persona PersonaID=new Persona();
    	PersonaID.setNombre(Identidad.getNombre());
    	PersonaID.setApellido(Identidad.getApellido());
    	PersonaID.setCedula(Identidad.getCedula());
    	PersonaID.setCorreo(Identidad.getCorreo());
    	PersonaID.setDireccion(Identidad.getDireccion());
    	PersonaID.setTelefono(Identidad.getTelefono());
    	PersonaID.setEstadoPersona(true);
    	mDAO.insertar(PersonaID);
    }
    public List<Salida> findAllSalidas(){
    	return mDAO.findAll(Salida.class);
    }
}
