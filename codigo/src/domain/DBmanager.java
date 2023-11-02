package domain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBmanager {
	//CREACION DE LA CONEXION
	private Connection conn;
	
	public void initConexion() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:bd.db");
	}
	
	public void cerrarConexion() throws SQLException {
		conn.close();
	}
	
	//METODOS DE LA BASE DE DATOS
	public List<Prenda> getPrendas(){
		String sql = "Select * from Prenda;";
		List<Prenda> prendas = new ArrayList<>();
		try(Statement stmt = conn.createStatement()){
			try (ResultSet rs = stmt.executeQuery(sql)){
				while (rs.next()) {
					Prenda prenda = new Prenda(
							Prenda.Tamanyo.valueOf(rs.getString("tamanyo")),
							Prenda.Tipo.valueOf(rs.getString("tipo")),
							Tienda.Proveedor.valueOf(rs.getString("proveedor")),
							rs.getString("nombre"),
							rs.getString("imagen"),
							rs.getInt("precio")
					);
					prendas.add(prenda);
				}
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		} catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return prendas;
	}
	
	public List<Tienda> getTiendas(){
		String sql = "Select * from Tienda;";
		List<Tienda> tiendas = new ArrayList<>();
		try (Statement stmt = conn.createStatement()){
			try (ResultSet rs = stmt.executeQuery(sql)){
				Tienda tienda = new Tienda(
						Tienda.Proveedor.valueOf(rs.getString("proveedor")),
						rs.getString("URI"),
						rs.getString("telefono"),
						rs.getString("direccion")
				);
				tiendas.add(tienda);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return tiendas;
	}
	
}
