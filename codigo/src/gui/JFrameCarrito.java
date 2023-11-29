package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import domain.Prenda;
import domain.Usuario;

public class JFrameCarrito extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private List<Prenda> prendas;
	private JPanelNavegacion panelNavegacion;
	
	public JFrameCarrito(Usuario usuario) {
		//CONFIGURACION DEL USUARIO
				this.usuario = new Usuario(usuario);
				//CONFIGURACION GENERAL
				Dimension tamanyoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
				this.setSize(1500, 800);
				this.setLocation((int)(tamanyoPantalla.width/2) - (int) (this.getSize().width/2), (int) (tamanyoPantalla.height/2) - (int) (this.getSize().height/2));
				this.setResizable(false);
				this.setVisible(true);
				this.setTitle("Ventana Principal");		
				this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				
				//CONFIGURACION DE VENTANA
					//JBUTTONs/JLABELs/JTABLEs/...
						//PANEL NAVEGACION
						panelNavegacion = new JPanelNavegacion(usuario, this);
						//PANEL 
						
						
						//LAYOUT
						this.getContentPane().setLayout(new GridBagLayout());
						
						GridBagConstraints gbcPanelNavegacion = new GridBagConstraints();
						gbcPanelNavegacion.gridx = 0;
						gbcPanelNavegacion.gridy = 0;
						gbcPanelNavegacion.weightx = 0;
						gbcPanelNavegacion.weighty = 0;
						gbcPanelNavegacion.fill = GridBagConstraints.BOTH;
						
						//JPANELs
						panelNavegacion.setBackground(new Color(35, 39, 42));
						
						
						
						panelNavegacion.setPreferredSize(new Dimension(1500, 70));
						panelNavegacion.setMinimumSize(panelNavegacion.getPreferredSize());
		
						
						panelNavegacion.setBackground(panelNavegacion.getBackground());
						panelNavegacion.getPanelLogo().setBackground(panelNavegacion.getBackground());
						panelNavegacion.getPanelLinks().setBackground(panelNavegacion.getBackground());
	}
}
