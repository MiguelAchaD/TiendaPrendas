package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.sql.SQLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import domain.DBmanager;
import domain.Usuario;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DBmanagerTest {
	static private DBmanager dbm;
	
	@BeforeAll
	void dataBaseLink() throws IOException{
		dbm = new DBmanager();
		try {
			dbm.initConexion();
		} catch (ClassNotFoundException | SQLException e) {
			fail("Error al inicializar la base de datos: " + e.getMessage());
		}
	}
	
	@Test
	void testMaximoPrecio() {
		int maximoPrecio = 850;
		int supuestoMaximoPrecio = dbm.getMaximoCoste();
		assertEquals(maximoPrecio, supuestoMaximoPrecio);
	}
	
	@Test
	void testCrearUsuario() {
	    // Usuario existente
	    String contrasenya = "123";
	    Usuario usuarioExistente = new Usuario("miguel", "acha", "miguel.acha", "porfavorApruebame@gmail.com");
	    boolean resultado = dbm.crearUsuario(usuarioExistente.getNombreUsuario(), usuarioExistente.getNombre(), usuarioExistente.getApellidos(), usuarioExistente.getMail(), contrasenya);
	    assertFalse(resultado, "Se esperaba que el usuario existente no pudiera ser creado");

	    // Usuario no existente
	    String generadorNombreUsuario = String.valueOf(Math.random() * 100000) + "." + String.valueOf(Math.random() * 1000) + "." + String.valueOf(Math.random() * 100);
	    String generadorCorreo = String.valueOf(Math.random() * 100000) + "@" + String.valueOf(Math.random() * 1000) + "." + String.valueOf(Math.random() * 100);
	    Usuario usuarioNoExistente = new Usuario("prog", "iii", generadorNombreUsuario, generadorCorreo);
	    resultado = dbm.crearUsuario(usuarioNoExistente.getNombreUsuario(), usuarioNoExistente.getNombre(), usuarioNoExistente.getApellidos(), usuarioNoExistente.getMail(), contrasenya);
	    assertTrue(resultado, "Se esperaba que el usuario no existente pudiera ser creado");
	}
	
	@AfterAll
	void dataBaseUnLink() {
		try {
			dbm.cerrarConexion();
		} catch (SQLException e) {
			fail("Error al cerrar la base de datos: " + e.getMessage());
		}
	}

}
