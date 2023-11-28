package domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBmanager {
	// CREACION DE LA CONEXION
	private Connection conn;

	public void initConexion() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:bd.db");
	}

	public void cerrarConexion() throws SQLException {
		conn.close();
	}

	// METODOS DE LA BASE DE DATOS
	public List<Proveedor> getProveedores() {
		String sql = "Select * from Proveedor;";
		List<Proveedor> proveedores = new ArrayList<>();
		try (Statement stmt = conn.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(sql)) {
				while (rs.next()) {
					Proveedor proveedor = new Proveedor(rs.getString("nombre"));
					proveedores.add(proveedor);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
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
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
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
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
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
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
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
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return tiendas;
	}

	public boolean existeUsuario(String nombreUsuario) {
		String sql = "SELECT COUNT(*) FROM Usuarios WHERE nombreUsuario = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nombreUsuario);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public boolean validarCredenciales(String nombreUsuario, String contrasena) {
		String sql = "SELECT COUNT(*) FROM Usuarios WHERE nombreUsuario = ? AND contrasena = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nombreUsuario);
			pstmt.setString(2, contrasena);
			try (ResultSet rs = pstmt.executeQuery()) {
				return rs.next() && rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public boolean crearUsuario(String nombreUsuario, String nombre, String apellido, String email, String contrasena) {
		if (existeUsuario(nombreUsuario)) {
			System.err.println("El usuario ya existe");
			return false;
		}

		String sql = "INSERT INTO Usuarios (nombreUsuario, nombre, apellido, email, contrasena) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, nombreUsuario);
			pstmt.setString(2, nombre);
			pstmt.setString(3, apellido);
			pstmt.setString(4, email);
			pstmt.setString(5, contrasena);

			pstmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

}
