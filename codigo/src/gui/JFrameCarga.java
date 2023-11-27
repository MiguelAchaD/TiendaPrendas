package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JFrameCarga extends JFrame {
	private static final long serialVersionUID = 1L;

	// VARIABLES
	JLabel imagen;

	public JFrameCarga() {
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
				Timer timer = new Timer(1500, actionEvent -> cerrarVentana());
				timer.setRepeats(false);
				timer.start();
			}
		});

		this.setVisible(true);
	}

	private void cerrarVentana() {
		this.dispose();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrameCarga carga = new JFrameCarga();
			carga.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent e) {
					// Colocar aquí el código que se ejecutará al cerrar la ventana manualmente
					System.out.println("La ventana se cerró manualmente.");
					System.exit(0);
				}
			});
		});
	}
}
