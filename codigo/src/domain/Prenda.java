package domain;

public class Prenda {
	//VARIABLES
	private Tamanyo tamanyo;
	private Tipo tipo;
	private Proveedor proveedor;
	private String nombre;
	private String imagen;
	private Integer precio;
	
	//CONSTRUCTORES
	public Prenda(Tamanyo tamanyo, Tipo tipo, Proveedor proveedor, String nombre, String imagen, Integer precio) {
		super();
		this.tamanyo = tamanyo;
		this.tipo = tipo;
		this.proveedor = proveedor;
		this.nombre = nombre;
		this.imagen = imagen;
		this.precio = precio;
	}
	
	//GETTERS Y SETTERS
	public Tamanyo getTamanyo() {
		return tamanyo;
	}

	public void setTamanyo(Tamanyo tamanyo) {
		this.tamanyo = tamanyo;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Integer getPrecio() {
		return precio;
	}

	public void setPrecio(Integer precio) {
		this.precio = precio;
	}

	//TO STRING
	@Override
	public String toString() {
		return "Prenda [tamanyo=" + tamanyo.getNombre() + ", tipo=" + tipo.getNombre() + ", proveedor=" + proveedor.getNombre() + ", nombre=" + nombre
				+ ", imagen=" + imagen + ", precio=" + precio + "]";
	}

}
