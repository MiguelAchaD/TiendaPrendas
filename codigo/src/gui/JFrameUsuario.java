package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import domain.DBmanager;
import domain.Usuario;
import io.HerramientasFicheros;

public class JFrameUsuario extends JFrame{
	private static final long serialVersionUID = 1L;
	
	//VARIABLES
	private Usuario usuario;
	private JPanel panelPrincipal;
	private JPanel panelImagen;
	private JLabel imagen;
	private JPanel panelNavegacion;
	private JPanel panelInformacion;
	private JTextArea nombre;
	private JTextArea apellidos;
	private JTextArea mail;
	private JTextArea nombreUsuario;
	private HerramientasFicheros hf;
	private Properties propiedades;
	
	public JFrameUsuario(Usuario usuario, DBmanager dbm) throws IOException {
		//CONFIGURACION USUARIO
		this.usuario = new Usuario(usuario);
		//CONFIGURACION GENERAL
		JFrameHerramientas herramienta = new JFrameHerramientas();
		herramienta.configurarVentanaGeneralNormal(this);
		this.getContentPane().setBackground(new Color(255,255,255));
		hf = new HerramientasFicheros();
		propiedades = hf.lectorPropiedades("conf/config.properties");
		//CONFIGURACION DE VENTANA
			//JBUTTONs/JLABELs/JTABLEs/...
				//PANEL NAVEGACION
				panelNavegacion = new JPanelNavegacion(usuario, this, dbm);
				//PANEL PRINCIPAL
				panelPrincipal = new JPanel();
				panelPrincipal.setLayout(new GridBagLayout());
				GridBagConstraints gbc = new GridBagConstraints();
				panelPrincipal.setSize(new Dimension((int)(this.getSize().width*0.7), (int)(this.getSize().height*0.7)));
				panelPrincipal.setBackground(new Color(255, 255, 255));
				
				panelImagen = new JPanel();
				panelImagen.setLayout(new FlowLayout(FlowLayout.LEFT));
				imagen = new JLabel(new ImageIcon(new ImageIcon(propiedades.getProperty("rutaImagenes") + "usuario.png").getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
				imagen.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
				panelImagen.add(imagen);
				panelImagen.setPreferredSize(new Dimension((int) (panelPrincipal.getSize().width*0.8), 120));
				panelImagen.setBackground(new Color(128, 128, 128));
				gbc.gridx = 0;
				gbc.gridy = 0;
				
				panelPrincipal.add(panelImagen, gbc);
				
				panelInformacion = new JPanel();
				panelInformacion.setBackground(new Color(165, 165, 165));
				panelInformacion.setLayout(new GridBagLayout());
				GridBagConstraints gbcPI = new GridBagConstraints();
				gbcPI.gridx = 0;
				gbcPI.gridy = 0;
				JPanel nombrePanel = new JPanel();
				nombrePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				nombrePanel.add(new JLabel("Nombre"));
				nombrePanel.setPreferredSize(new Dimension(200, 20));
				nombrePanel.setBackground(panelInformacion.getBackground());
				panelInformacion.add(nombrePanel, gbcPI);
				gbcPI.gridx = 1;
				nombre = new JTextArea();
				nombre.setText(usuario.getNombre());
				nombre.setPreferredSize(new Dimension(150,20));
				panelInformacion.add(nombre, gbcPI);
				gbcPI.gridx = 0;
				gbcPI.gridy = 1;
				JPanel apellidosPanel = new JPanel();
				apellidosPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				apellidosPanel.add(new JLabel("Apellidos"));
				apellidosPanel.setPreferredSize(new Dimension(200, 20));
				apellidosPanel.setBackground(panelInformacion.getBackground());
				panelInformacion.add(apellidosPanel, gbcPI);	
				gbcPI.gridx = 1;
				apellidos = new JTextArea();
				apellidos.setText(usuario.getApellidos());
				apellidos.setPreferredSize(new Dimension(150, 20));
				panelInformacion.add(apellidos, gbcPI);
				gbcPI.gridx = 0;
				gbcPI.gridy = 2;
				JPanel mailPanel = new JPanel();
				mailPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				mailPanel.add(new JLabel("Email"));
				mailPanel.setPreferredSize(new Dimension(200, 20));
				mailPanel.setBackground(panelInformacion.getBackground());
				panelInformacion.add(mailPanel, gbcPI);
				gbcPI.gridx = 1;
				mail = new JTextArea();
				mail.setText(usuario.getMail());
				mail.setPreferredSize(new Dimension(150, 20));
				panelInformacion.add(mail, gbcPI);
				gbcPI.gridx = 0;
				gbcPI.gridy = 3;
				JPanel nombreUsuarioPanel = new JPanel();
				nombreUsuarioPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
				nombreUsuarioPanel.add(new JLabel("Nombre de Usuario"));
				nombreUsuarioPanel.setPreferredSize(new Dimension(200, 20));
				nombreUsuarioPanel.setBackground(panelInformacion.getBackground());
				panelInformacion.add(nombreUsuarioPanel, gbcPI);
				gbcPI.gridx = 1;
				nombreUsuario = new JTextArea();
				nombreUsuario.setText(usuario.getNombreUsuario());
				nombreUsuario.setPreferredSize(new Dimension(150, 20));
				panelInformacion.add(nombreUsuario, gbcPI);
				
				gbc.gridy = 1;
				panelInformacion.setPreferredSize(new Dimension((int) (panelPrincipal.getSize().width*0.8), (int) (panelPrincipal.getSize().height*0.6)));
				panelPrincipal.add(panelInformacion, gbc);
			
			//TODO
			this.getContentPane().setSize(this.getSize());
			
			//LAYOUT
			this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
			
			this.getContentPane().add(panelNavegacion);

			this.getContentPane().add(panelPrincipal);
			
			//JPANELs
	}
}
