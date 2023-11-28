package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import domain.DBmanager;
import domain.Prenda;
import domain.Proveedor;
import domain.Tamanyo;
import domain.Tipo;
import gui.JFrameCarga;
import gui.JFrameLogIn;
import gui.JFramePrincipal;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// APERTURA DE LA BD
		DBmanager dbm = new DBmanager();
		dbm.initConexion();

		// LLAMADA A APERTURA DE VENTANAS
		SwingUtilities.invokeLater(() -> {
		    new JFrameCarga(dbm);
		});
	}
}
