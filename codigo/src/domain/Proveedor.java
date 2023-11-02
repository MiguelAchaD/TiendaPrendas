package domain;

public class Proveedor {
	private String nombre;

	public Proveedor(String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Proveedor [nombre=" + nombre + "]";
	}
	
	
	
}
