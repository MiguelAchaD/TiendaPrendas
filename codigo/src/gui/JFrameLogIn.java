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
import javax.swing.WindowConstants;

public class JFrameLogIn extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public JFrameLogIn() {
		//CONFIGURACION DE VENTANA
		
		
		//CONFIGURACION GENERAL	
		Dimension tamanyoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int tamanyo = tamanyoPantalla.height/2;
		this.setSize(tamanyo, tamanyo);
		this.setLocation((int)(tamanyoPantalla.width/2) - (int) (this.getSize().width/2), (int) (tamanyoPantalla.height/2) - (int) (this.getSize().height/2));
		this.setVisible(true);
		this.setTitle("Ventana Principal");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
