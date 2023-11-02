package domain;

public class Tienda {
	
	//VARIABLES
	private Proveedor proveedor;
	private String URI;
	private String telefono;
	private String direccion;
	
	//CONSTRUCTORES
	public Tienda(Proveedor proveedor, String uRI, String telefono, String direccion) {
		super();
		this.proveedor = proveedor;
		this.URI = uRI;
		this.telefono = telefono;
		this.direccion = direccion;
	}
	
	//GETTERS Y SETTERS
	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getURI() {
		return URI;
	}

	public void setURI(String uRI) {
		this.URI = uRI;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	//TO STRING
	@Override
	public String toString() {
		return "Tienda [proveedor=" + proveedor.getNombre() + ", URI=" + URI + ", telefono=" + telefono + ", direccion=" + direccion
				+ "]";
	}
	
	
}
