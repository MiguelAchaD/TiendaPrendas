package domain;

public class Usuario {
	private String nombre;
	private String apellidos;
	private String nombreUsuario;
	private String mail;
	
	public Usuario(String nombre, String apellidos, String nombreUsuario, String mail) {
		super();
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.nombreUsuario = nombreUsuario;
		this.mail = mail;
	}
	
	public Usuario(Usuario u) {
		super();
		this.nombre = u.getNombre();
		this.apellidos = u.getApellidos();
		this.nombreUsuario = u.getNombreUsuario();
		this.mail = u.getMail();
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
}
