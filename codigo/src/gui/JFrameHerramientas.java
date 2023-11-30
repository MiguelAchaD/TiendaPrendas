package gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class JFrameHerramientas {
	
	public void configurarVentanaGeneralNormal(JFrame frame) {
		Dimension tamanyoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((int) (tamanyoPantalla.width*0.9), (int) (tamanyoPantalla.height*0.8));
		frame.setMinimumSize(new Dimension(1000, 700));
		frame.setLocation((int)(tamanyoPantalla.width/2) - (int) (frame.getSize().width/2), (int) (tamanyoPantalla.height/2) - (int) (frame.getSize().height/2));
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setTitle("Ventana Principal");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
