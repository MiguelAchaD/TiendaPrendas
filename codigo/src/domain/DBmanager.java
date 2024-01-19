package domain;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import io.HerramientasFicheros;

public class DBmanager {
	// CREACION DE LA CONEXION
	private Connection conn;
	private HerramientasFicheros hf;
	private Properties propiedades;
	private Logger logger;
	
	public DBmanager() throws IOException {
		hf = new HerramientasFicheros();
		propiedades = hf.lectorPropiedades("conf/config.properties");
		logger = HerramientasFicheros.getLogger();
	}

	public void initConexion() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:" + propiedades.getProperty("rutaBaseDeDatos") + "bd.db");
	}

	public void cerrarConexion() throws SQLException {
		conn.close();
	}

	// METODOS DE LA BASE DE DATOS
	public int getMaximoCoste() {
		int coste = 500;
		String sql = "Select max(precio) from Prenda;";
		try(Statement stmt = conn.createStatement()){
			try(ResultSet rs = stmt.executeQuery(sql)){
				int precioSupuesto = rs.getInt(1);
				if (Integer.valueOf(precioSupuesto) > 0) {
					coste = Integer.valueOf(precioSupuesto);
				}
			}
		} catch (SQLException e) {
			logger.warning("Error al buscar el maximo coste - " + e.getMessage());
		}
		return coste;
	}
	
	public List<Proveedor> getProveedores() {
		String sql = "Select * from Proveedor;";
		List<Proveedor> proveedores = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					Proveedor proveedor = new Proveedor(rs.getString("nombre"));
					proveedores.add(proveedor);
				}
			}
		} catch (Exception e) {
			logger.warning("Error al buscar lo proveedores - " + e.getMessage());
		}
		return proveedores;
	}

	public Proveedor matchProveedor(List<Proveedor> proveedores, String nombreProveedor) {
		Proveedor match;
		for (Proveedor proveedor : proveedores) {
			if (proveedor.getNombre().equals(nombreProveedor)) {
				match = proveedor;
				return match;
			}
		}
		return null;
	}

	public List<Tipo> getTipos() {
		String sql = "Select * from Tipo;";
		List<Tipo> tipos = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					Tipo tipo = new Tipo(rs.getString("nombre"));
					tipos.add(tipo);
				}
			}
		} catch (Exception e) {
			logger.warning("Error al buscar los tipos - " + e.getMessage());
		}
		return tipos;
	}

	public Tipo matchTipo(List<Tipo> tipos, String nombreTipo) {
		Tipo match;
		for (Tipo tipo : tipos) {
			if (tipo.getNombre().equals(nombreTipo)) {
				match = tipo;
				return match;
			}
		}
		return null;
	}

	public List<Tamanyo> getTamanyos() {
		String sql = "Select * from Tamanyo;";
		List<Tamanyo> tamanyos = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					Tamanyo tamanyo = new Tamanyo(rs.getString("nombre"));
					tamanyos.add(tamanyo);
				}
			}
		} catch (Exception e) {
			logger.warning("Error al buscar los tamanyos - " + e.getMessage());
		}
		return tamanyos;
	}

	public Tamanyo matchTamanyo(List<Tamanyo> tamanyos, String nombreTamanyo) {
		Tamanyo match;
		for (Tamanyo tamanyo : tamanyos) {
			if (tamanyo.getNombre().equals(nombreTamanyo)) {
				match = tamanyo;
				return match;
			}
		}
		return null;
	}

	public List<Prenda> getPrendas() {
		String sql = "Select * from Prenda;";
		List<Prenda> prendas = new ArrayList<>();
		List<Tipo> tipos = getTipos();
		List<Tamanyo> tamanyos = getTamanyos();
		List<Proveedor> proveedores = getProveedores();
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					Prenda prenda = new Prenda(matchTamanyo(tamanyos, rs.getString("tamanyo")),
							matchTipo(tipos, rs.getString("tipo")),
							matchProveedor(proveedores, rs.getString("proveedor")), rs.getString("nombre"),
							rs.getString("imagen"), rs.getInt("precio"));
					prendas.add(prenda);
				}
			}
		} catch (Exception e) {
			logger.warning("Error al buscar las prendas - " + e.getMessage());
		}
		return prendas;
	}

	public List<Tienda> getTiendas() {
		String sql = "Select * from Tienda;";
		List<Tienda> tiendas = new ArrayList<>();
		List<Proveedor> proveedores = getProveedores();
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sql)) {
				Tienda tienda = new Tienda(matchProveedor(proveedores, rs.getString("proveedor")), rs.getString("URI"),
						rs.getString("telefono"), rs.getString("direccion"));
				tiendas.add(tienda);
			}
		} catch (Exception e) {
			logger.warning("Error al buscar las tiendas - " + e.getMessage());
		}
		return tiendas;
	}

	public boolean existeUsuario(String nombreUsuario, String mail) {
		String sql = "SELECT COUNT(*) FROM Usuario WHERE (nombreUsuario = ? and mail = ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nombreUsuario);
			pstmt.setString(2, mail);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			logger.warning("Error al verificar usuario - " + e.getMessage());
			return false;
		}
	}

	public boolean validarCredenciales(String nombreUsuario, String contrasena) {
		String sql = "SELECT COUNT(*) FROM Usuario WHERE nombreUsuario = ? AND contrasena = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nombreUsuario);
			pstmt.setString(2, contrasena);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			logger.warning("Error al validar las credenciales - " + e.getMessage());
			return false;
		}
	}

	public boolean crearUsuario(String nombreUsuario, String nombre, String apellidos, String mail, String contrasena) {
		if (existeUsuario(nombreUsuario, mail)) {
			logger.warning("Usuario ya existente.");
			return false;
		}

		String sql = "INSERT INTO Usuario (nombreUsuario, nombre, apellidos, mail, contrasena) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nombreUsuario);
			pstmt.setString(2, nombre);
			pstmt.setString(3, apellidos);
			pstmt.setString(4, mail);
			pstmt.setString(5, contrasena);

			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			logger.warning("Error al crear el nuevo usuario - " + e.getMessage());
			return false;
		}
	}
	
	public Usuario usuarioPorNombre(String nombreUsuario) {
	    String sql = "SELECT * FROM Usuario WHERE nombreUsuario = ?";
	    
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, nombreUsuario);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                String nombre = rs.getString("nombre");
	                String apellidos = rs.getString("apellidos");
	                String mail = rs.getString("mail");
	                return new Usuario(nombre, apellidos, nombreUsuario, mail);
	            } else {
	            	logger.warning("Usuario no encontrado - No existe el usuario");
	                return null;
	            }
	        }
	    } catch (SQLException e) {
	    	logger.warning("Usuario no encontrado - " + e.getMessage());
	        return null;
	    }
	}

}
