package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;

public class JFrameCarga extends JFrame{
	private static final long serialVersionUID = 1L;

	//VARIABLES
	JLabel imagen;
	
	public JFrameCarga() {		
		//CONFIGURACION DE VENTANA
		imagen = new JLabel();
		ImageIcon imagenOriginal = new ImageIcon("images/icono.png");
		Image imagenEscalada = imagenOriginal.getImage().getScaledInstance(250, 250, Image.SCALE_DEFAULT);
		imagen.setIcon(new ImageIcon(imagenEscalada));
		
		this.getContentPane().setLayout(new GridBagLayout());
		this.getContentPane().add(imagen);
		this.getContentPane().setBackground(Color.white);
		
		//CONFIGURACION GENERAL	
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		Dimension tamanyoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int tamanyo = tamanyoPantalla.height/2;
		this.setSize(tamanyo, tamanyo);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		//CIERRE
		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.dispose();
	}
}
