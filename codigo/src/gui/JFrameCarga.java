package gui;

import javax.swing.*;

import domain.DBmanager;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class JFrameCarga extends JFrame {
	private static final long serialVersionUID = 1L;

	// VARIABLES
	JLabel imagen;

	public JFrameCarga(DBmanager dbm) {
		// CONFIGURACION DE VENTANA
		imagen = new JLabel();
		ImageIcon imagenOriginal = new ImageIcon("images/icono.png");
		Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
		imagen.setIcon(new ImageIcon(imagenEscalada));

		this.getContentPane().setLayout(new GridBagLayout());
		this.getContentPane().add(imagen);
		this.getContentPane().setBackground(Color.white);

		// CONFIGURACION GENERAL
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		Dimension tamanyoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int tamanyo = tamanyoPantalla.height / 2;
		this.setSize(tamanyo, tamanyo);
		this.setLocation((int) (tamanyoPantalla.width / 2) - (int) (this.getSize().width / 2),
				(int) (tamanyoPantalla.height / 2) - (int) (this.getSize().height / 2));

		// Agregar WindowListener para cerrar la ventana
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				//Carga de datos
				try {
					// TODO: Cargar datos
					Thread.sleep(1000);
					dispose();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				SwingUtilities.invokeLater(() -> new JFrameLogIn(dbm).setVisible(true));
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (e.getWindow() instanceof JFrameCarga) {
					try {
						dbm.cerrarConexion();
						System.out.println("Conexión a bd cerrada");
						System.exit(1);
					} catch (SQLException e1) {
						System.out.println(e1.getMessage());
					}
				}
			}
		});

		this.setVisible(true);
	}

}
