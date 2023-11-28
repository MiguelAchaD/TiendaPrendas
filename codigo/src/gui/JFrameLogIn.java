package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.text.Document;

import domain.DBmanager;

public class JFrameLogIn extends JFrame {

	private static final long serialVersionUID = 1L;

	private static Boolean estadoLogin = false;

	private JLabel logo;
	private JTextField usuarioTextField;
	private JTextField mailTextField;
	private JTextField nombreTextField;
	private JTextField apellidosTextField;
	private JPasswordField passwordField;
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
		iniciarSesionButton = new JButton("Iniciar Sesión");
		registroButton = new JButton("Registrarse");
		registroLabel = new JLabel("<html><u style='color: blue'>¿No tienes una cuenta? Regístrate gratis!</u></html>");
		inicioSesionLabel = new JLabel("<html><u style='color: blue'>¿Ya tienes una cuenta? Inicia sesión!</u></html>");

		// DECLARACION DE LOS ESTADOS Y SUS VALORES
		HashMap<Boolean, List<HashMap<JComponent, String>>> estado = new HashMap<>();
		// DECLARACION DE LOS COMPONENTES
		HashMap<JComponent, String> label = new HashMap<>();
		List<HashMap<JComponent, String>> inicioDeSesionJComponents = new ArrayList<>();
		label.put(usuarioTextField, "Usuario:");
		inicioDeSesionJComponents.add(label);
		label.put(passwordField, "Contraseña:");
		inicioDeSesionJComponents.add(label);
		label.put(usuarioTextField, null);
		inicioDeSesionJComponents.add(label);
		label.put(registroLabel, null);
		inicioDeSesionJComponents.add(label);
		List<HashMap<JComponent, String>> registroJComponents = new ArrayList<>();
		label.put(nombreTextField, "Nombre:");
		registroJComponents.add(label);
		label.put(apellidosTextField, "Apellidos:");
		registroJComponents.add(label);
		label.put(mailTextField, "Email:");
		registroJComponents.add(label);
		label.put(passwordField, "Contraseña:");
		registroJComponents.add(label);
		label.put(registroButton, null);
		registroJComponents.add(label);
		label.put(inicioSesionLabel, null);
		registroJComponents.add(label);

		estado.put(true, inicioDeSesionJComponents);
		estado.put(false, registroJComponents);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(20, 5, 20, 5);

		// LOGO
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		add(logo, gbc);

		initFrame(gbc, estado, getFrame());

		// CAMBIAR A REGISTRO
		registroLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				initFrame(gbc, estado, getFrame());
			}
		});

		inicioSesionLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				initFrame(gbc, estado, getFrame());
			}
		});

		// INICIAR SESION
		iniciarSesionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String usuario = usuarioTextField.getText();
				char[] password = passwordField.getPassword();

				JOptionPane.showMessageDialog(JFrameLogIn.this, "Iniciar Sesión: Usuario - " + usuario);
			}
		});

//		this.dispose();
//		SwingUtilities.invokeLater(() -> new JFramePrincipal(dbm).setVisible(true));

		// CONFIGURACION GENERAL
		Dimension tamanyoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		int tamanyo = (tamanyoPantalla.height / 2);
		this.setSize(tamanyo, tamanyo);
		this.setLocation((int) (tamanyoPantalla.width / 2) - (int) (this.getSize().width / 2),
				(int) (tamanyoPantalla.height / 2) - (int) (this.getSize().height / 2));
		this.setVisible(true);
		this.setTitle("Ventana Principal");
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private static void initFrame(GridBagConstraints gbc, HashMap<Boolean, List<HashMap<JComponent, String>>> estado,
			JFrame frame) {
		List<HashMap<JComponent, String>> componentes = estado.get(estadoLogin);
		HashMap<JComponent, String> componente;
		for (int i = 0; i < componentes.size(); i++) {
			componente = componentes.get(i);
			System.out.println(componente.values());
			gbc.gridy = i;
//			System.out.println(componente.values().toArray()[0].equals(null));
			if (componente.values().toArray()[0] != null) {
				gbc.gridx = 1;
				frame.add(new JLabel(componente.values().toArray()[0].toString()));
			}
			gbc.gridx = 0;
			frame.add((JComponent) componente.keySet().toArray()[0]);
		}
		estadoLogin = !estadoLogin;
	}

}
