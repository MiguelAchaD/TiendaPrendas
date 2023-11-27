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
			createJFrames(dbm);
		});
	}

	private static void createJFrames(DBmanager dbm) {
		// APERTURA DE VENTANAS
		SwingUtilities.invokeLater(() -> {
		    JFrameCarga carga = new JFrameCarga();
		    carga.addWindowListener(new WindowAdapter() {
		        @Override
		        public void windowClosing(WindowEvent e) {
		            try {
		                if (e.getWindow() instanceof JFrameCarga) {
		                    dbm.cerrarConexion();
		                    System.out.println("dbClosed");
		                    carga.dispose();
		                    SwingUtilities.invokeLater(() -> new JFrameLogIn().setVisible(true));
		                } else {
							carga.dispose();
							SwingUtilities.invokeLater(() -> new JFramePrincipal(dbm).setVisible(true));
						}
		            } catch (SQLException e1) {
		                System.err.println(e1.getMessage());
		            }
		        }
		    });
		});

		SwingUtilities.invokeLater(() -> {
			JFrameLogIn login = new JFrameLogIn();
			login.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					try {
						if (e.getWindow() instanceof JFrameLogIn) {
							dbm.cerrarConexion();
							System.out.println("dbClosed");
							login.dispose();
							SwingUtilities.invokeLater(() -> new JFramePrincipal(dbm).setVisible(true));
						} else {
							login.dispose();
							SwingUtilities.invokeLater(() -> new JFramePrincipal(dbm).setVisible(true));
						}
					} catch (SQLException e1) {
						System.err.println(e1.getMessage());
					}
				}
			});
		});

	}

}
