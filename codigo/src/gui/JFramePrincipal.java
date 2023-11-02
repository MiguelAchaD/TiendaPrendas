package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import domain.DBmanager;
import domain.Prenda.Tipo;
import domain.Tienda;
import domain.Tienda.Proveedor;

public class JFramePrincipal extends JFrame{
	private static final long serialVersionUID = 1L;
	
	//VARIABLES
	private JPanel panelNavegacion = new JPanel();
	private JPanel navegacion = new JPanel();
	private JPanel panelBusqueda = new JPanel();
	private JPanel busqueda = new JPanel();
	private JPanel panelPrendas = new JPanel();
	private JButton botonLogo = new JButton("LOGO");
	private JButton botonUsuario = new JButton("USUARIO");
	private JTextField textoFiltro = new JTextField();
	private JComboBox<String> opcionTipo = new JComboBox<>();
	private JComboBox<String> opcionProveedor = new JComboBox<>();
	private JTable tablaPrendas = new JTable();
	private JScrollPane prendas = new JScrollPane(tablaPrendas);
	private DefaultTableModel modeloTablaPrendas = new DefaultTableModel();
	
	public JFramePrincipal() {
		//CONFIGURACION GENERAL
		Dimension tamanyoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(tamanyoPantalla.width/2)-750, (int) (tamanyoPantalla.height/2)-400);
		this.setMinimumSize(new Dimension(1500, 770));
		this.setVisible(true);
		this.setTitle("Ventana Principal");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//CONFIGURACION DE VENTANA
			//LAYOUT
			this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
			//JPANELs
			panelNavegacion.setBackground(Color.white);
			panelNavegacion.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.black));
			panelBusqueda.setBackground(Color.white);
			panelBusqueda.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black));
			panelPrendas.setBackground(Color.white);
			panelNavegacion.setPreferredSize(new Dimension(1500, 70));
			panelNavegacion.setMinimumSize(panelNavegacion.getPreferredSize());
			panelBusqueda.setPreferredSize(new Dimension(1500, 50));
			panelBusqueda.setMinimumSize(panelBusqueda.getPreferredSize());
			panelPrendas.setPreferredSize(new Dimension(1500, 550));
			panelPrendas.setMinimumSize(panelPrendas.getPreferredSize());
			this.getContentPane().add(panelNavegacion);
			this.getContentPane().add(panelBusqueda);
			this.getContentPane().add(panelPrendas);
			pack();
			//JBUTTONs/JLABELs/JTABLEs/...
				//PANEL NAVEGACION
				panelNavegacion.setLayout(new GridBagLayout());
				botonLogo.setBorder(BorderFactory.createMatteBorder(20, 50, 20, 50, Color.gray));
				botonLogo.setBackground(Color.gray);
				botonLogo.addActionListener(botonLogoAL);
				botonUsuario.setBorder(BorderFactory.createMatteBorder(20, 50, 20, 50, Color.gray));
				botonUsuario.setBackground(Color.gray);
				botonUsuario.addActionListener(botonUsuarioAL);
				navegacion.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
				navegacion.setBackground(Color.white);
				panelNavegacion.add(navegacion);
				navegacion.add(botonLogo);
				navegacion.add(botonUsuario);
				//PANEL BUSQUEDA
				panelBusqueda.setLayout(new GridBagLayout());
				textoFiltro.setPreferredSize(new Dimension(400,25));
				busqueda.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
				busqueda.setBackground(Color.white);
				panelBusqueda.add(busqueda);
				textoFiltro.getDocument().addDocumentListener(textFiltroDL);
				opcionTipo.addItemListener(opcionTipoItL);
				opcionProveedor.addItemListener(opcionProveedorItL);
				busqueda.add(textoFiltro);
				busqueda.add(opcionTipo);
				busqueda.add(opcionProveedor);
				//PANEL PRENDAS
				panelPrendas.setLayout(new GridBagLayout());
				prendas.setLayout(new ScrollPaneLayout());
				prendas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				prendas.setPreferredSize(new Dimension(1400, 500));
				tablaPrendas.setTableHeader(null);
				panelPrendas.add(prendas);
	}
	
	
	
	public JTable getTablaPrendas() {
		return tablaPrendas;
	}


	public void setTablaPrendas(JTable tablaPrendas) {
		this.tablaPrendas = tablaPrendas;
	}


	public DefaultTableModel getModeloTablaPrendas() {
		return modeloTablaPrendas;
	}


	public void setModeloTablaPrendas(DefaultTableModel modeloTablaPrendas) {
		this.modeloTablaPrendas = modeloTablaPrendas;
	}
	
	public JComboBox<String> getOpcionTipo() {
		return opcionTipo;
	}


	public void setOpcionTipo(JComboBox<String> opcionTipo) {
		this.opcionTipo = opcionTipo;
	}


	public JComboBox<String> getOpcionProveedor() {
		return opcionProveedor;
	}


	public void setOpcionProveedor(JComboBox<String> opcionProveedor) {
		this.opcionProveedor = opcionProveedor;
	}


	//FUNCIONALIDAD NAVEGACION
	final ActionListener botonLogoAL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Logo");
			
		}
	};
	final ActionListener botonUsuarioAL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Usuario");
			
		}
	};
	
	
	//FUNCIONALIDAD BUSQUEDA
	final DocumentListener textFiltroDL = new DocumentListener() {
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			System.out.println("Texto");
			
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			System.out.println("Texto");
			
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			System.out.println("Texto");
			
		}
	};
	
	final ItemListener opcionTipoItL = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			System.out.println("Tipo diferente");
			
		}
	};
	
	final ItemListener opcionProveedorItL = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			System.out.println("Proveedor diferente");
			
		}
	};
	
	List<String> getBusqueda() {
		String texto = String.valueOf(textoFiltro.getAccessibleContext());
		String tipo = String.valueOf(opcionTipo.getSelectedItem());
		String proveedor = String.valueOf(opcionProveedor.getSelectedItem());
		List<String> valores = new ArrayList<String>();
		valores.add(texto);
		valores.add(tipo);
		valores.add(proveedor);
		return valores;
	}
	
	public String toString() {
		return "principal";
	}
	
}
