package minimarketdemo.model.core.entities;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the productos database table.
 * 
 */
@Entity
@Table(name="productos")
@NamedQuery(name="Producto.findAll", query="SELECT p FROM Producto p")
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_producto", unique=true, nullable=false)
	private Integer idProducto;

	@Column(name="cod_producto", nullable=false, length=10)
	private String codProducto;

	@Column(nullable=false, length=50)
	private String descripcion;

	@Column(name="nombre_producto", nullable=false, length=50)
	private String nombreProducto;

	@Column(name="precio_compra_prod", nullable=false, precision=131089)
	private BigDecimal precioCompraProd;

	@Column(name="stock_productos", nullable=false)
	private Integer stockProductos;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="id_categoria", nullable=false)
	private Categoria categoria;

	//bi-directional many-to-one association to Proveedore
	@ManyToOne
	@JoinColumn(name="id_proveedor", nullable=false)
	private Proveedore proveedore;

	//bi-directional many-to-one association to Stock
	@OneToMany(mappedBy="producto")
	private List<Stock> stocks;

	public Producto() {
	}

	public Integer getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getCodProducto() {
		return this.codProducto;
	}

	public void setCodProducto(String codProducto) {
		this.codProducto = codProducto;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombreProducto() {
		return this.nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public BigDecimal getPrecioCompraProd() {
		return this.precioCompraProd;
	}

	public void setPrecioCompraProd(BigDecimal precioCompraProd) {
		this.precioCompraProd = precioCompraProd;
	}

	public Integer getStockProductos() {
		return this.stockProductos;
	}

	public void setStockProductos(Integer stockProductos) {
		this.stockProductos = stockProductos;
	}

	public Categoria getCategoria() {
		return this.categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Proveedore getProveedore() {
		return this.proveedore;
	}

	public void setProveedore(Proveedore proveedore) {
		this.proveedore = proveedore;
	}

	public List<Stock> getStocks() {
		return this.stocks;
	}

	public void setStocks(List<Stock> stocks) {
		this.stocks = stocks;
	}

	public Stock addStock(Stock stock) {
		getStocks().add(stock);
		stock.setProducto(this);

		return stock;
	}

	public Stock removeStock(Stock stock) {
		getStocks().remove(stock);
		stock.setProducto(null);

		return stock;
	}

}