package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.WindowConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import domain.DBmanager;
import domain.Prenda;
import domain.Proveedor;
import domain.Tamanyo;
import domain.Tipo;

public class JFramePrincipal extends JFrame{
	private static final long serialVersionUID = 1L;
	
	//VARIABLES
	private JPanel panelNavegacion;
	private JPanel panelLogo;
	private JPanel panelLinks;
	private JPanel panelBusqueda = new JPanel();
	private JPanel busqueda = new JPanel();
	private JPanel panelPrendas;
	private JButton botonLogo;
	private JButton botonUsuario;
	private JButton botonCarrito;
	private JTextField textoFiltro = new JTextField();
	private JComboBox<String> opcionTipo;
	private JComboBox<String> opcionProveedor;
	private JComboBox<String> opcionTamanyo;
	private JTable tablaPrendas;
	private JScrollPane prendas;
	private DefaultTableModel modeloTablaPrendas;
	private JPanel funcionalidad;
	private JButton anyadirCarrito;
	private JButton masInfo;
	
	public JFramePrincipal(DBmanager dbm) {
		//CONFIGURACION GENERAL
		Dimension tamanyoPantalla = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int)(tamanyoPantalla.width/2) - (int) (this.getSize().width/2), (int) (tamanyoPantalla.height/2) - (int) (this.getSize().height/2));
		this.setMinimumSize(new Dimension(1500, 800));
		this.setVisible(true);
		this.setTitle("Ventana Principal");		
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		//CONFIGURACION DE VENTANA
			//JBUTTONs/JLABELs/JTABLEs/...
				//PANEL NAVEGACION
				panelNavegacion = new JPanel();
				panelNavegacion.setLayout(new BorderLayout());
				
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
				panelNavegacion.add(panelLogo, BorderLayout.WEST);
				panelNavegacion.add(panelLinks, BorderLayout.EAST);
				//PANEL BUSQUEDA
				panelBusqueda.setLayout(new GridBagLayout());
				textoFiltro.setPreferredSize(new Dimension(400,25));
				busqueda.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
				panelBusqueda.add(busqueda);
				textoFiltro.getDocument().addDocumentListener(textFiltroDL);
				opcionTipo = new JComboBox<>();
				opcionProveedor = new JComboBox<>();
				opcionTamanyo = new JComboBox<>();
				busqueda.add(textoFiltro);
				busqueda.add(opcionTipo);
				busqueda.add(opcionProveedor);
				busqueda.add(opcionTamanyo);
				opcionTipo.addItemListener(opcionTipoItL);
				opcionProveedor.addItemListener(opcionProveedorItL);
				opcionTamanyo.addItemListener(opcionTamanyoItL);
				//PANEL PRENDAS
				panelPrendas = new JPanel();
				tablaPrendas = new JTable();
				modeloTablaPrendas = new DefaultTableModel();
				prendas = new JScrollPane(tablaPrendas);
				panelPrendas.setLayout(new GridBagLayout());
				
				GridBagConstraints gbcPrendas = new GridBagConstraints();
				gbcPrendas.gridx = 0;
				gbcPrendas.gridy = 0;
				gbcPrendas.weightx = 1;
				gbcPrendas.weighty = 1;
				gbcPrendas.fill = GridBagConstraints.BOTH;
				
				prendas.setLayout(new ScrollPaneLayout());
				prendas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				prendas.setPreferredSize(new Dimension(1400, 500));
				prendas.getVerticalScrollBar().setUnitIncrement(25);
				panelPrendas.add(prendas, gbcPrendas);
				
				tablaPrendas.setTableHeader(null);

				GridBagConstraints gbcFuncionalidad = new GridBagConstraints();
				gbcFuncionalidad.gridx = 0;
				gbcFuncionalidad.gridy = 1;
				gbcFuncionalidad.fill = GridBagConstraints.HORIZONTAL;
				
				funcionalidad = new JPanel();
				funcionalidad.setLayout(new FlowLayout());
				
				anyadirCarrito = new JButton(new ImageIcon(new ImageIcon("images/cart.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
				anyadirCarrito.setBackground(Color.white);
				masInfo = new JButton(new ImageIcon(new ImageIcon("images/info.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
				masInfo.setBackground(Color.white);
				
				funcionalidad.add(anyadirCarrito);
				funcionalidad.add(masInfo);

				panelPrendas.add(funcionalidad, gbcFuncionalidad);
				
				//LAYOUT
				this.getContentPane().setLayout(new GridBagLayout());
				
				GridBagConstraints gbcPanelNavegacion = new GridBagConstraints();
				gbcPanelNavegacion.gridx = 0;
				gbcPanelNavegacion.gridy = 0;
				gbcPanelNavegacion.weightx = 0;
				gbcPanelNavegacion.weighty = 0;
				gbcPanelNavegacion.fill = GridBagConstraints.BOTH;
	
				GridBagConstraints gbcPanelBusqueda = new GridBagConstraints();
				gbcPanelBusqueda.gridx = 0;
				gbcPanelBusqueda.gridy = 1;
				gbcPanelBusqueda.weightx = 0;
				gbcPanelBusqueda.weighty = 0;
				gbcPanelBusqueda.fill = GridBagConstraints.BOTH;
	
				GridBagConstraints gbcPanelPrendas = new GridBagConstraints();
				gbcPanelPrendas.gridx = 0;
				gbcPanelPrendas.gridy = 2;
				gbcPanelPrendas.weightx = 1;
				gbcPanelPrendas.weighty = 1;
				gbcPanelPrendas.fill = GridBagConstraints.BOTH;
				
				//JPANELs
				panelNavegacion.setBackground(new Color(35, 39, 42));
				panelBusqueda.setBackground(new Color(114, 137, 218));
				panelPrendas.setBackground(new Color(35, 39, 42));
				
				panelNavegacion.setPreferredSize(new Dimension(1500, 70));
				panelNavegacion.setMinimumSize(panelNavegacion.getPreferredSize());
				
				panelBusqueda.setPreferredSize(new Dimension(1500, 50));
				panelBusqueda.setMinimumSize(panelBusqueda.getPreferredSize());
				
				panelPrendas.setPreferredSize(new Dimension(1500, 550));
				panelPrendas.setMinimumSize(panelPrendas.getPreferredSize());
				
				panelNavegacion.setBackground(panelNavegacion.getBackground());
				busqueda.setBackground(panelBusqueda.getBackground());
				funcionalidad.setBackground(panelPrendas.getBackground());
				panelLinks.setBackground(panelNavegacion.getBackground());
				panelLogo.setBackground(panelNavegacion.getBackground());
				
				this.getContentPane().add(panelNavegacion, gbcPanelNavegacion);
				this.getContentPane().add(panelBusqueda, gbcPanelBusqueda);
				this.getContentPane().add(panelPrendas, gbcPanelPrendas);
				
				pack();
				
			initTables();
			loadBusqueda(this, dbm);
			loadTablePrendas(this.getTablaPrendas(), dbm);
	}
	

	public void initTables() {
		TableCellRenderer tcr = (table, value, isSelected, hasFocus, row, column) -> {
			JPanel result = new JPanel(new GridBagLayout());
			result.setBackground(Color.white);
			JLabel texto = new JLabel("");
			
			if (column == 0) {
				texto.setIcon(new ImageIcon((new ImageIcon(value.toString()).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT))));
				texto.setBackground(result.getBackground());
			} else if (column == 5) {
				texto.setText(value.toString() + " $");
				texto.setFont(new Font("Arial", Font.BOLD, 25));
				texto.setForeground(Color.white);
				texto.setVerticalAlignment(JLabel.BOTTOM);
				texto.setBackground(new Color(17, 140, 79));
				texto.setBorder(BorderFactory.createMatteBorder(10, 20, 10, 20, new Color(17, 140, 79)));
//			} else if (column == 3){
//				texto.setIcon(new ImageIcon((new ImageIcon("images/proveedores/" + value.toString() + ".png").getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH))));
//				texto.setBackground(result.getBackground());
			} else {
				texto.setText(value.toString());
				texto.setFont(new Font("Arial", Font.BOLD, 15));
				texto.setBackground(result.getBackground());
			}
			
			texto.setHorizontalAlignment(JLabel.CENTER);
			texto.setVerticalAlignment(JLabel.CENTER);
			texto.setOpaque(true);
			result.add(texto);
			
			return result;
		};
		this.tablaPrendas.setDefaultRenderer(Object.class, tcr);
		this.tablaPrendas.setDefaultEditor(Object.class, null);
		this.tablaPrendas.setShowVerticalLines(false);
	}
	
	private void loadTablePrendas(JTable tabla, DBmanager dbm) {
		DefaultTableModel modeloTabla;
		tabla.setModel(modeloTabla = new DefaultTableModel());
		
		String[] columns = {"", "", "", "", "", ""};
		for (String column : columns) {
		    modeloTabla.addColumn(column);
		}
		
		Integer[] widths = {400, 200, 100, 50, 50, 50};
		TableColumnModel tcm = tabla.getColumnModel();
		for (int i = 0; i < widths.length; i++) {
			tcm.getColumn(i).setPreferredWidth(widths[i]);
		}
		tabla.setRowHeight(400);
		
		List<Prenda> prendas = dbm.getPrendas();
		for (Prenda prenda : prendas) {
			modeloTabla.addRow(new Object[] {prenda.getImagen(), prenda.getNombre(), prenda.getTamanyo().getNombre(),  prenda.getProveedor().getNombre(),
					prenda.getTipo().getNombre().toLowerCase(), prenda.getPrecio()});
		}
	}
	
	private void loadBusqueda(JFramePrincipal frame, DBmanager dbm) {
		//INICIALIZACION DE DATOS DEL PROGRAMA QUE ESTAN EN LA BD
		List<Proveedor> proveedores = dbm.getProveedores();
		List<Tipo> tipos = dbm.getTipos();
		List<Tamanyo> tamanyos = dbm.getTamanyos();
		//INICIALIZAR BUSQUEDA
		for (Proveedor elemento : proveedores) {
			frame.getOpcionProveedor().addItem(elemento.getNombre().replace("_", " "));
		}
		
		for (Tipo elemento : tipos) {
			frame.getOpcionTipo().addItem(elemento.getNombre().toLowerCase());
		}
		
		for (Tamanyo elemento : tamanyos) {
			frame.getOpcionTamanyo().addItem(elemento.getNombre());
		}
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

	public JComboBox<String> getOpcionTamanyo() {
		return opcionTamanyo;
	}
	
	public void setOpcionTamanyo(JComboBox<String> opcionTamanyo) {
		this.opcionTamanyo = opcionTamanyo;
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
	
	final ActionListener botonCarritoAL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Carrito");
			
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
		public void changedUpdate(DocumentEvent e) {}
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
	
	final ItemListener opcionTamanyoItL = new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			System.out.println("Tamanyo diferente");
			
		}
	};
	
	List<String> getBusqueda() {
		String texto = String.valueOf(textoFiltro.getAccessibleContext());
		String tipo = String.valueOf(opcionTipo.getSelectedItem());
		String proveedor = String.valueOf(opcionProveedor.getSelectedItem());
		String tamanyo = String.valueOf(opcionTamanyo.getSelectedItem());
		List<String> valores = new ArrayList<String>();
		valores.add(texto);
		valores.add(tipo);
		valores.add(proveedor);
		valores.add(tamanyo);
		return valores;
	}
	
	public String toString() {
		return "principal";
	}
	
}
