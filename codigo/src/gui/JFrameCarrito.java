package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneLayout;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import domain.DBmanager;
import domain.Prenda;
import domain.Proveedor;
import domain.Tamanyo;
import domain.Tipo;
import domain.Usuario;
import io.HerramientasFicheros;

public class JFrameCarrito extends JFrame {
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private List<Prenda> prendas;
	private JPanelNavegacion panelNavegacion;
	private JPanel panelCarrito;
	private JPanel panelCompra;
	private JTable tablaPrendas;
	private DefaultTableModel modeloTablaPrendas;
	private JScrollPane prendasSP;
	private JButton confirmar;
	private JButton eliminar;
	private JLabel precioTotal;
	private HerramientasFicheros hf;
	private Properties propiedades;
	private JDialog dialogoCompra;

	public JFrameCarrito(Usuario usuario, DBmanager dbm) throws IOException {
		// CONFIGURACION DEL USUARIO
		this.usuario = new Usuario(usuario);
		// CONFIGURACION GENERAL
		JFrameHerramientas herramientas = new JFrameHerramientas();
		herramientas.configurarVentanaGeneralNormal(this);
		this.getContentPane().setBackground(new Color(255,255,255));
		hf = new HerramientasFicheros();
		propiedades = hf.lectorPropiedades("conf/config.properties");
		dialogoCompra = new JDialog(this);
		
		// CONFIGURACION DE VENTANA
			// JBUTTONs/JLABELs/JTABLEs/...
				// PANEL NAVEGACION
				panelNavegacion = new JPanelNavegacion(usuario, this, dbm);
				// PANEL CARRITO
				panelCarrito = new JPanel();
				panelCarrito.setLayout( new GridBagLayout());
				tablaPrendas = new JTable();
				tablaPrendas.setTableHeader(null);
				tablaPrendas.setTableHeader(null);
				prendasSP = new JScrollPane(tablaPrendas);
				
				panelCarrito.setLayout(new GridBagLayout());
				
				GridBagConstraints gbcPrendas = new GridBagConstraints();
				gbcPrendas.gridx = 0;
				gbcPrendas.gridy = 0;
				gbcPrendas.weightx = 1;
				gbcPrendas.weighty = 1;
				gbcPrendas.fill = GridBagConstraints.BOTH;
				gbcPrendas.insets = new Insets(50, 50, 50, 50);
				
				prendasSP.setLayout(new ScrollPaneLayout());
				prendasSP.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				prendasSP.setPreferredSize(new Dimension((int) (panelCarrito.getSize().width * 0.7), (int) (panelCarrito.getSize().width * 0.85)));
				prendasSP.getVerticalScrollBar().setUnitIncrement(25);
				panelCarrito.add(prendasSP, gbcPrendas);
				
				//PANEL COMPRA
				panelCompra = new JPanel();
				confirmar = new JButton("Comprar");
				confirmar.setPreferredSize(new Dimension(200, 40));
				eliminar = new JButton("Eliminar");
				eliminar.setPreferredSize(new Dimension(200, 40));
				
				panelCompra.setLayout(new GridBagLayout());
				
				GridBagConstraints gbcCompra = new GridBagConstraints();
				gbcCompra.gridx = 0;
				gbcCompra.gridy = 0;
				gbcCompra.weightx = 1;
				gbcCompra.weighty = 1;
				
				GridBagConstraints gbcEliminar = new GridBagConstraints();
				gbcEliminar.gridx = 1;
				gbcEliminar.gridy = 0;
				gbcEliminar.weightx = 1;
				gbcEliminar.weighty = 1;
				
				panelCompra.add(confirmar, gbcCompra);
				panelCompra.add(eliminar, gbcEliminar);
		
		
		// LAYOUT
		this.getContentPane().setLayout(new GridBagLayout());

		GridBagConstraints gbcPanelNavegacion = new GridBagConstraints();
		gbcPanelNavegacion.gridx = 0;
		gbcPanelNavegacion.gridy = 0;
		gbcPanelNavegacion.weightx = 0;
		gbcPanelNavegacion.weighty = 0;
		gbcPanelNavegacion.fill = GridBagConstraints.BOTH;
		
		GridBagConstraints gbcPanelCarrito = new GridBagConstraints();
		gbcPanelCarrito.gridx = 0;
		gbcPanelCarrito.gridy = 1;
		gbcPanelCarrito.weightx = 0;
		gbcPanelCarrito.weighty = 0;
		gbcPanelCarrito.fill = GridBagConstraints.BOTH;
		
		GridBagConstraints gbcPanelCompra = new GridBagConstraints();
		gbcPanelCompra.gridx = 0;
		gbcPanelCompra.gridy = 2;
		gbcPanelCompra.weightx = 0;
		gbcPanelCompra.weighty = 0;
		gbcPanelCompra.fill = GridBagConstraints.BOTH;

		// JPANELs
			//PANEL NAVEGACION
			panelNavegacion.setBackground(new Color(35, 39, 42));
	
			panelNavegacion.setPreferredSize(new Dimension(this.getSize().width, 70));
			panelNavegacion.setMinimumSize(panelNavegacion.getPreferredSize());
	
			panelNavegacion.setBackground(panelNavegacion.getBackground());
			panelNavegacion.getPanelLogo().setBackground(panelNavegacion.getBackground());
			panelNavegacion.getPanelLinks().setBackground(panelNavegacion.getBackground());
			//PANEL CARRITO
			panelCarrito.setBackground(this.getBackground());
			
			panelCarrito.setPreferredSize(new Dimension(this.getSize().width, (int) (this.getSize().height * 0.76)));
			panelCarrito.setMinimumSize(panelCarrito.getPreferredSize());			
			
			//PANEL COMPRA
			panelCompra.setBackground(new Color(114, 137, 218));
			
			panelCompra.setPreferredSize(new Dimension(this.getSize().width, (int) (this.getSize().height * 0.15)));
			panelCompra.setMinimumSize(panelCompra.getPreferredSize());
			
			
		//LAYOUT
		this.getContentPane().add(panelNavegacion, gbcPanelNavegacion);
		this.getContentPane().add(panelCarrito, gbcPanelCarrito);
		this.getContentPane().add(panelCompra, gbcPanelCompra);
		
		loadTableCarrito(tablaPrendas);
		initTables();
	}
	
	public void initTables() {
		TableCellRenderer tcr = (table, value, isSelected, hasFocus, row, column) -> {
			JPanel result = new JPanel(new GridBagLayout());
			result.setBackground(Color.white);
			JLabel texto = new JLabel("");
			GridBagConstraints gbcMas = new GridBagConstraints();
			gbcMas.gridx = 2;
			gbcMas.insets = new Insets(1, 1, 1, 1);
			GridBagConstraints gbcMenos = new GridBagConstraints();
			gbcMenos.gridx = 0;
			gbcMenos.insets = new Insets(1, 1, 1, 1);
			GridBagConstraints gbcSumatorio = new GridBagConstraints();
			gbcSumatorio.gridx = 1;
			gbcSumatorio.insets = new Insets(1, 7, 1, 7);
			
			if (column == 0) {
				texto.setIcon(new ImageIcon((new ImageIcon(propiedades.getProperty("rutaImagenes") + value.toString()).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT))));
				texto.setBackground(result.getBackground());
			} else if (column == 6) {
				texto.setText(value.toString() + " $");
				texto.setFont(new Font("Arial", Font.BOLD, 25));
				texto.setForeground(Color.white);
				texto.setVerticalAlignment(JLabel.BOTTOM);
				texto.setBackground(new Color(17, 140, 79));
				texto.setBorder(BorderFactory.createMatteBorder(10, 20, 10, 20, new Color(17, 140, 79)));
			} else {
				texto.setText(value.toString().replace("_", " "));
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

	public void loadTableCarrito(JTable tabla) {
		DefaultTableModel modeloTabla;
		tabla.setModel(modeloTabla = new DefaultTableModel());
		
		String[] columns = {"", "", "", "", "", ""};
		for (String column : columns) {
		    modeloTabla.addColumn(column);
		}
		
		Integer[] widths = {400, 200, 100, 50, 50};
		TableColumnModel tcm = tabla.getColumnModel();
		for (int i = 0; i < widths.length; i++) {
			tcm.getColumn(i).setPreferredWidth(widths[i]);
		}
		tabla.setRowHeight(400);
		
		List<Prenda> prendasALeer = HerramientasFicheros.leerCsv(propiedades.getProperty("rutaFicheros") + "carrito.csv");
		if (prendasALeer.size() > 0) {
			for (Prenda prenda : prendasALeer) {
				modeloTabla.addRow(new Object[] {prenda.getImagen(), prenda.getNombre(), prenda.getTamanyo().getNombre(),  prenda.getProveedor().getNombre(),
						prenda.getTipo().getNombre().toLowerCase(), prenda.getPrecio()});
			}
		}
	}
	
	final ActionListener comprarAL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	};
		
	final ActionListener eliminarAL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
		}
	};
}
