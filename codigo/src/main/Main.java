package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import domain.DBmanager;
import gui.JFrameCarga;
import io.HerramientasFicheros;

public class Main {
	private static Logger logger;
	private static HerramientasFicheros hf;
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {
		// CONFIGURACION LOGGER
		hf = new HerramientasFicheros();
		logger = HerramientasFicheros.getLogger();
		hf.configurarLogger();
		
		// APERTURA DE LA BD
		DBmanager dbm = new DBmanager();
		dbm.initConexion();
		
		// LLAMADA A APERTURA DE VENTANAS
		SwingUtilities.invokeLater(() -> {
		    try {
				new JFrameCarga(dbm);
			} catch (IOException e) {
				logger.warning("Error al invocar el programa: " + e.getMessage());
			}
		});
	}
}
