package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import domain.Usuario;

public class JPanelNavegacion extends JPanel {
	private Usuario usuario;
	private JButton botonLogo;
	private JButton botonUsuario;
	private JButton botonCarrito;
	private JPanel panelLogo;
	private JPanel panelLinks;
	private JFrame emisor;

	public JPanelNavegacion(Usuario usuario, JFrame emisor) {
		this.emisor = emisor;
		this.usuario = new Usuario(usuario);

		this.setLayout(new BorderLayout());

		botonLogo = new JButton("LOGO");
		botonLogo.setBorder(BorderFactory.createMatteBorder(20, 50, 20, 50, Color.white));
		botonLogo.setBackground(Color.white);
		botonLogo.addActionListener(botonLogoAL);

		botonUsuario = new JButton("USUARIO");
		botonUsuario.setBorder(BorderFactory.createMatteBorder(20, 50, 20, 50, Color.white));
		botonUsuario.setBackground(Color.white);
		botonUsuario.addActionListener(botonUsuarioAL);

		botonCarrito = new JButton("CARRITO");
		botonCarrito.setBorder(BorderFactory.createMatteBorder(20, 50, 20, 50, Color.white));
		botonCarrito.setBackground(Color.white);
		botonCarrito.addActionListener(botonCarritoAL);

		panelLogo = new JPanel();
		panelLinks = new JPanel();
		panelLogo.add(botonLogo);
		panelLinks.add(botonCarrito);
		panelLinks.add(botonUsuario);
		this.add(panelLogo, BorderLayout.WEST);
		this.add(panelLinks, BorderLayout.EAST);
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
			System.out.println("Logo");

		}
	};

	final ActionListener botonCarritoAL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(emisor instanceof JFrameCarrito)) {
				emisor.dispose();
				SwingUtilities.invokeLater(() -> {new JFrameCarrito(usuario);});
			}
		}
	};

	final ActionListener botonUsuarioAL = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(emisor instanceof JFrameUsuario)) {
				emisor.dispose();
				SwingUtilities.invokeLater(() -> {new JFrameUsuario(usuario);});
			}
		}
	};
}
