package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.DBmanager;
import domain.Usuario;
import io.HerramientasFicheros;

public class JPanelNavegacion extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private JButton botonLogo;
	private JButton botonUsuario;
	private JButton botonCarrito;
	private JPanel panelLogo;
	private JPanel panelLinks;
	private JFrame emisor;
	private HerramientasFicheros hf;
	private Properties propiedades;
	private DBmanager dbm;
	private Logger logger;

	public JPanelNavegacion(Usuario usuario, JFrame emisor, DBmanager dbm) throws IOException {
		this.emisor = emisor;
		this.usuario = new Usuario(usuario);
		this.dbm = dbm;
		
		hf = new HerramientasFicheros();;
		propiedades = hf.lectorPropiedades("conf/config.properties");
		logger = HerramientasFicheros.getLogger();

		this.setLayout(new BorderLayout());
		this.setBackground(new Color(35, 39, 42));

		botonLogo = new JButton("LOGO");
		botonLogo.setBorder(BorderFactory.createMatteBorder(20, 50, 20, 50, Color.white));
		botonLogo.setBackground(Color.white);
		botonLogo.addActionListener(botonLogoAL);

		botonUsuario = new JButton(new ImageIcon(
				new ImageIcon(propiedades.getProperty("rutaImagenes") + "usuario.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
		botonUsuario.setBackground(this.getBackground());
		botonUsuario.setBorder(BorderFactory.createMatteBorder(10, 20, 10, 20, this.getBackground()));
		botonUsuario.addActionListener(botonUsuarioAL);

		botonCarrito = new JButton(new ImageIcon(
				new ImageIcon(propiedades.getProperty("rutaImagenes") + "carrito.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
		botonCarrito.setBackground(this.getBackground());
		botonCarrito.setBorder(BorderFactory.createMatteBorder(10, 20, 10, 20, this.getBackground()));
		botonCarrito.addActionListener(botonCarritoAL);

		panelLogo = new JPanel();
		panelLinks = new JPanel();
		panelLogo.add(botonLogo);
		panelLinks.add(botonCarrito);
		panelLinks.add(botonUsuario);
		panelLogo.setBackground(this.getBackground());
		panelLinks.setBackground(this.getBackground());
		this.add(panelLogo, BorderLayout.WEST);
		this.add(panelLinks, BorderLayout.EAST);
		this.setPreferredSize(new Dimension(emisor.getSize().width, 70));
		this.setMaximumSize(new Dimension(emisor.getSize().width, 70));
	}

	public JPanel getPanelLogo() {
		return panelLogo;
	}

	public void setPanelLogo(JPanel panelLogo) {
		this.panelLogo = panelLogo;
	}

	public JPanel getPanelLinks() {
		return panelLinks;
	}

	public void setPanelLinks(JPanel panelLinks) {
		this.panelLinks = panelLinks;
	}

	final ActionListener botonLogoAL = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(emisor instanceof JFramePrincipal)) {
				emisor.dispose();
				SwingUtilities.invokeLater(() -> {
					try {
						new JFramePrincipal(dbm, usuario);
					} catch (IOException e1) {
						logger.warning("Error al invocar la ventana Principal: " + e1.getMessage());
					}
				});
			}
		}
	};

	final ActionListener botonCarritoAL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(emisor instanceof JFrameCarrito)) {
				emisor.dispose();
				SwingUtilities.invokeLater(() -> {
					try {
						new JFrameCarrito(usuario, dbm);
					} catch (IOException e1) {
						logger.warning("Error al invocar la ventana Carrito: " + e1.getMessage());
					}
				});
			}
		}
	};

	final ActionListener botonUsuarioAL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(emisor instanceof JFrameUsuario)) {
				emisor.dispose();
				SwingUtilities.invokeLater(() -> {
					try {
						new JFrameUsuario(usuario, dbm);
					} catch (IOException e1) {
						logger.warning("Error al invocar la ventana de Usuario: " + e1.getMessage());
					}
				});
			}
		}
	};
}
