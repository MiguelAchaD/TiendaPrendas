package gui;

import javax.swing.*;

import domain.DBmanager;
import io.HerramientasFicheros;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class JFrameCarga extends JFrame {
	private static final long serialVersionUID = 1L;

	// VARIABLES
	private JLabel imagen;
	private Properties propiedades;
	private HerramientasFicheros hf = new HerramientasFicheros();
	private Logger logger;

	public JFrameCarga(DBmanager dbm) throws IOException {
		//CONFIGURACION DE LOGGER
		logger = HerramientasFicheros.getLogger();
		
		// CONFIGURACION DE VENTANA
		imagen = new JLabel();
		propiedades = hf.lectorPropiedades("conf/config.properties");
		ImageIcon imagenOriginal = new ImageIcon(propiedades.getProperty("rutaImagenes") + "icono.png");
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
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					Thread.sleep(1000);
					dispose();
				} catch (InterruptedException e1) {
					logger.warning(e1.getMessage());
				}
				SwingUtilities.invokeLater(() -> {
					try {
						new JFrameLogIn(dbm).setVisible(true);
					} catch (IOException e1) {
						logger.warning("Error al invocar la ventana de logIn: " + e1.getMessage());
					}
				});
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (e.getWindow() instanceof JFrameCarga) {
					try {
						dbm.cerrarConexion();
						logger.info("Conexión a bd cerrada");
						System.exit(1);
					} catch (SQLException e1) {
						logger.warning(e1.getMessage());
					}
				}
			}
		});

		this.setVisible(true);
	}

}
