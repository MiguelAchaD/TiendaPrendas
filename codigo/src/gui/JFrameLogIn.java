package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import domain.DBmanager;
import domain.Usuario;

public class JFrameLogIn extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Boolean estadoLogin = false;

	private JLabel logo;
	private JTextField usuarioTextField;
	private JTextField mailTextField;
	private JTextField nombreTextField;
	private JTextField apellidosTextField;
	private JPasswordField passwordField;
	private JPasswordField passwordField2;
	private JButton iniciarSesionButton;
	private JButton registroButton;
	private JLabel registroLabel;
	private JLabel inicioSesionLabel;

	private JFrameLogIn getFrame() {
		return this;
	}

	public JFrameLogIn(DBmanager dbm) {

		// CONFIGURACION DE VENTANA
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (e.getWindow() instanceof JFrameLogIn) {
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

		// DECLARACION DE VARIABLES
		logo = new JLabel("TiendaDeRopa");
		logo.setFont(new Font("Arial", Font.BOLD, 35));
		usuarioTextField = new JTextField(20);
		mailTextField = new JTextField(20);
		nombreTextField = new JTextField(20);
		apellidosTextField = new JTextField(20);
		passwordField = new JPasswordField(20);
		passwordField2 = new JPasswordField(20);
		iniciarSesionButton = new JButton("Iniciar Sesión");
		registroButton = new JButton("Registrarse");
		registroLabel = new JLabel("<html><u>¿No tienes una cuenta? Regístrate gratis</u></html>");
		registroLabel.setForeground(new Color(0, 0, 255));
		inicioSesionLabel = new JLabel("<html><u>¿Ya tienes una cuenta? Inicia sesión</u></html>");
		inicioSesionLabel.setForeground(new Color(0, 0, 255));

		// DECLARACION DE LOS ESTADOS Y SUS VALORES
		HashMap<Boolean, List<JComponent>> estado = new HashMap<>();
		// DECLARACION DE LOS COMPONENTES
		List<JComponent> inicioDeSesionJComponents = new ArrayList<>();
		inicioDeSesionJComponents.add(usuarioTextField);
		inicioDeSesionJComponents.add(passwordField);
		inicioDeSesionJComponents.add(iniciarSesionButton);
		inicioDeSesionJComponents.add(registroLabel);
		List<JComponent> registroJComponents = new ArrayList<>();
		registroJComponents.add(mailTextField);
		registroJComponents.add(nombreTextField);
		registroJComponents.add(apellidosTextField);
		registroJComponents.add(usuarioTextField);
		registroJComponents.add(passwordField);
		registroJComponents.add(passwordField2);
		registroJComponents.add(registroButton);
		registroJComponents.add(inicioSesionLabel);

		estado.put(true, inicioDeSesionJComponents);
		estado.put(false, registroJComponents);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getID() == KeyEvent.KEY_PRESSED) {
					if (estadoLogin == true) {
						iniciarSesion(dbm, inicioDeSesionJComponents);
					} else {
						registrar(dbm, registroJComponents);
					}
				}
				return false;
			}
		});

		HashMap<JComponent, String> compLabel = new HashMap<>();
		compLabel.put(nombreTextField, "Nombre");
		compLabel.put(apellidosTextField, "Apellidos");
		compLabel.put(mailTextField, "Email");
		compLabel.put(usuarioTextField, "Usuario");
		compLabel.put(passwordField, "Contraseña");
		compLabel.put(passwordField2, "Repita la contraseña");

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(20, 5, 20, 5);

		addLinkListener(inicioSesionLabel, gbc, estado, compLabel);
		addLinkListener(registroLabel, gbc, estado, compLabel);
		addInicioSesionClickAction(dbm, iniciarSesionButton, inicioDeSesionJComponents);
		addRegistroClickAction(dbm, registroButton, registroJComponents);

		// LOGO
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(logo, gbc);

		initFrame(gbc, estado, getFrame(), compLabel);

		// CONFIGURACION GENERAL
		Dimension tamanyoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int tamanyo = ((int) (tamanyoPantalla.height * 0.7));
		this.setSize(tamanyo, tamanyo);
		this.setLocation((int) (tamanyoPantalla.width / 2) - (int) (this.getSize().width / 2),
				(int) (tamanyoPantalla.height / 2) - (int) (this.getSize().height / 2));
		this.setVisible(true);
		this.setTitle("Ventana Principal");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setResizable(false);
	}

	public void removeComponents(JFrame frame) {
		Component[] components = frame.getContentPane().getComponents();
		for (int i = 1; i < components.length; i++) {
			frame.getContentPane().remove(components[i]);
		}
	}

	private void initFrame(GridBagConstraints gbc, HashMap<Boolean, List<JComponent>> estado, JFrame frame,
			HashMap<JComponent, String> compLabel) {

		if (frame.getContentPane().getComponents().length > 1) {
			removeComponents(frame);
		}

		List<JComponent> componentes = estado.get(!estadoLogin);
		for (int i = 0; i < componentes.size(); i++) {
			JComponent comp = componentes.get(i);
			gbc.gridy = i + 1;
			if (compLabel.keySet().contains(comp)) {
				gbc.gridwidth = 1;
				JPanel label = new JPanel();
				label.setPreferredSize(new Dimension(122, 20));
				label.setLayout(new FlowLayout(FlowLayout.RIGHT));
				JLabel texto = new JLabel(compLabel.get(comp));
				label.add(texto);
				frame.getContentPane().add(label, gbc);
				gbc.gridx = 1;
			}
			frame.getContentPane().add(comp, gbc);
			gbc.gridx = 0;
			gbc.gridwidth = 2;
			frame.revalidate();
			frame.repaint();
		}

		estadoLogin = !estadoLogin;
	}

	private void addLinkListener(JComponent comp, GridBagConstraints gbc, HashMap<Boolean, List<JComponent>> estado,
			HashMap<JComponent, String> compLabel) {
		comp.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				comp.setForeground(new Color(0, 128, 255));
			}

			public void mouseExited(MouseEvent evt) {
				comp.setForeground(new Color(0, 0, 255));
			}

			public void mouseClicked(MouseEvent e) {
				initFrame(gbc, estado, getFrame(), compLabel);
			}
		});

	}

	private void addInicioSesionClickAction(DBmanager dbm, JComponent comp, List<JComponent> comps) {
		comp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				iniciarSesion(dbm, comps);
			}
		});
	}

	private void addRegistroClickAction(DBmanager dbm, JComponent comp, List<JComponent> comps) {
		comp.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				registrar(dbm, comps);
			}
		});
	}

	private boolean getEstadoComponentes(List<JComponent> componentes) {
		boolean vacios = false;
		for (JComponent componente : componentes) {
			if ((componente instanceof JPasswordField
					&& ((String.valueOf(JPasswordField.class.cast(componente).getPassword()).length() == 0)))
					|| (componente instanceof JTextField
							&& ((JTextField.class.cast(componente)).getText().length() == 0))) {
				vacios = true;
			}

		}
		return vacios;
	}

	private void iniciarSesion(DBmanager dbm, List<JComponent> comps) {
		if (!getEstadoComponentes(comps)) {
			if (dbm.validarCredenciales(usuarioTextField.getText(),
					DigestUtils.sha1Hex(String.valueOf(passwordField.getPassword())))) {
				Usuario u = dbm.usuarioPorNombre(usuarioTextField.getText());
				if (u != null) {
					getFrame().dispose();
					SwingUtilities.invokeLater(() -> new JFramePrincipal(dbm, u));
				} else {
					System.err.println("error al encontrar el usuario");
				}
			} else {
				System.err.println("nombre de usuario o contraseña incorrectos!");
			}
		} else {
			System.err.println("rellene todo los campos");
		}
	}

	private void registrar(DBmanager dbm, List<JComponent> comps) {
		if (!getEstadoComponentes(comps)) {
			if (String.valueOf(passwordField.getPassword()).equals(String.valueOf(passwordField2.getPassword()))) {
				if (!dbm.existeUsuario(usuarioTextField.getText(), mailTextField.getText())) {
					getFrame().dispose();
					dbm.crearUsuario(usuarioTextField.getText(), nombreTextField.getText(),
							apellidosTextField.getText(), mailTextField.getText(),
							DigestUtils.sha1Hex(String.valueOf(passwordField.getPassword())));

					SwingUtilities.invokeLater(() -> new JFramePrincipal(dbm, new Usuario(nombreTextField.getText(),
							apellidosTextField.getText(), usuarioTextField.getText(), mailTextField.getText())));
				} else {
					System.err.println("cuenta existente");
				}
			} else {
				System.err.println("contraseñas no coinciden");
			}
		} else {
			System.err.println("rellene todo los campos");
		}
	}
}
