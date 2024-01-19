package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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

public class JFramePrincipal extends JFrame{
	private static final long serialVersionUID = 1L;
	
	//VARIABLES
	private JPanelNavegacion panelNavegacion;
	private JPanel panelBusqueda = new JPanel();
	private JPanel busqueda = new JPanel();
	private JPanel panelPrendas;
	private JTextField textoFiltro = new JTextField();
	private JComboBox<String> opcionTipo;
	private JComboBox<String> opcionProveedor;
	private JComboBox<String> opcionTamanyo;
	private JSlider precioMaximo;
	private JLabel precioMaximoLabel;
	private JButton nombreAlfabetico;
	private JButton consultar;
	private JTable tablaPrendas;
	private JScrollPane prendas;
	private DefaultTableModel modeloTablaPrendas;
	private JPanel funcionalidad;
	private JButton anyadirCarrito;
	private Usuario usuario;
	private List<Prenda> listaPrendas;
	private KeyEventDispatcher ked;
	private HerramientasFicheros hf;
	private Properties propiedades;
	private static Logger logger;
	private Comparator<Prenda> comparatorPrendasAlfabetico;
	
	public JFramePrincipal(DBmanager dbm, Usuario usuario) throws IOException {
		//CONFIGURACION DEL USUARIO
		this.usuario = new Usuario(usuario);
		//CONFIGURACION GENERAL
		JFrameHerramientas herramientas = new JFrameHerramientas();
		logger = HerramientasFicheros.getLogger();
		herramientas.configurarVentanaGeneralNormal(this);
		hf = new HerramientasFicheros();
		propiedades = hf.lectorPropiedades("conf/config.properties");
		comparatorPrendasAlfabetico = new Comparator<Prenda>() {
			@Override
			public int compare(Prenda p1, Prenda p2) {
				return p1.getNombre().compareTo(p2.getNombre());
			}
		};
		
		//CONFIGURACION DE VENTANA
			//JBUTTONs/JLABELs/JTABLEs/...
				//PANEL NAVEGACION
				panelNavegacion = new JPanelNavegacion(usuario, this, dbm);
				//PANEL BUSQUEDA
				panelBusqueda.setLayout(new GridBagLayout());
				textoFiltro.setPreferredSize(new Dimension(400,25));
				busqueda.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 0));
				panelBusqueda.add(busqueda);
				opcionTipo = new JComboBox<>();
				opcionProveedor = new JComboBox<>();
				opcionTamanyo = new JComboBox<>();
				int costeMaximo = dbm.getMaximoCoste();
				precioMaximo = new JSlider(JSlider.HORIZONTAL, 10, costeMaximo, costeMaximo);
				precioMaximo.setPreferredSize(new Dimension(350, 40));
				precioMaximo.setMajorTickSpacing((int) (costeMaximo/10));
				precioMaximo.setPaintLabels(true);
				precioMaximo.setPaintTicks(true);
				precioMaximo.setBackground(new Color(114, 137, 218));
				precioMaximo.addChangeListener(precioMaximoCL);
				precioMaximoLabel = new JLabel(String.valueOf(precioMaximo.getValue()) + " $");
				precioMaximoLabel.setFont(new Font("Arial", Font.BOLD, 15));
				nombreAlfabetico = new JButton("Ordenar Alfabéticamente");
				nombreAlfabetico.addActionListener(ordenAlfabeticoListener);
				consultar = new JButton("Buscar");
				consultar.addActionListener(consultaAL);
				busqueda.add(textoFiltro);
				busqueda.add(opcionTipo);
				busqueda.add(opcionProveedor);
				busqueda.add(opcionTamanyo);
				busqueda.add(precioMaximo);
				busqueda.add(precioMaximoLabel);
				busqueda.add(nombreAlfabetico);
				busqueda.add(consultar);
				
				KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(ked = new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (e.getKeyCode() == KeyEvent.VK_ENTER && e.getID() == KeyEvent.KEY_PRESSED) {
							consultar.doClick();
							return true;
						}
						return false;
					}
				});
				
				//PANEL PRENDAS
				panelPrendas = new JPanel();
				tablaPrendas = new JTable();
				prendas = new JScrollPane(tablaPrendas);
				panelPrendas.setLayout(new GridBagLayout());
				
				modeloTablaPrendas = new DefaultTableModel();
				
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
				
				anyadirCarrito = new JButton(new ImageIcon(new ImageIcon(propiedades.getProperty("rutaImagenes") + "cart.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
				anyadirCarrito.addActionListener(carritoAL);
				anyadirCarrito.setBackground(Color.white);
				
				funcionalidad.add(anyadirCarrito);

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
				panelBusqueda.setBackground(new Color(114, 137, 218));
				panelPrendas.setBackground(new Color(35, 39, 42));
				
				panelBusqueda.setPreferredSize(new Dimension(this.getSize().width, 50));
				panelBusqueda.setMinimumSize(panelBusqueda.getPreferredSize());
				
				panelPrendas.setPreferredSize(new Dimension(this.getSize().width, 550));
				panelPrendas.setMinimumSize(panelPrendas.getPreferredSize());
				
				busqueda.setBackground(panelBusqueda.getBackground());
				funcionalidad.setBackground(panelPrendas.getBackground());
				
				this.getContentPane().add(panelNavegacion, gbcPanelNavegacion);
				this.getContentPane().add(panelBusqueda, gbcPanelBusqueda);
				this.getContentPane().add(panelPrendas, gbcPanelPrendas);
								
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
				texto.setIcon(new ImageIcon((new ImageIcon(propiedades.getProperty("rutaImagenes") + value.toString()).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT))));
				texto.setBackground(result.getBackground());
			} else if (column == 5) {
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
			
			if (isSelected) {
				result.setBackground(new Color(153,204,255));
				if (column != 5) {
					texto.setBackground(new Color(153,204,255));
				}
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
		
		listaPrendas = dbm.getPrendas();
		Collections.shuffle(listaPrendas);
		for (Prenda prenda : listaPrendas) {
			modeloTabla.addRow(new Object[] {prenda.getImagen(), prenda.getNombre(), prenda.getTamanyo().getNombre(),  prenda.getProveedor().getNombre(),
					prenda.getTipo().getNombre().toLowerCase(), prenda.getPrecio()});
		}
	}
	
	private void loadTablePrendas(JTable tabla, List<Prenda> prendas) {
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
		frame.getOpcionProveedor().addItem("--Todos--");
		for (Proveedor elemento : proveedores) {
			frame.getOpcionProveedor().addItem(elemento.getNombre().replace("_", " "));
		}
		
		frame.getOpcionTipo().addItem("--Todos--");
		for (Tipo elemento : tipos) {
			frame.getOpcionTipo().addItem(elemento.getNombre().toLowerCase());
		}
		
		frame.getOpcionTamanyo().addItem("--Todos--");
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
	
	public JSlider getPrecioMaximo() {
		return precioMaximo;
	}

	public void setPrecioMaximo(JSlider precioMaximo) {
		this.precioMaximo = precioMaximo;
	}

	public JLabel getPrecioMaximoLabel() {
		return precioMaximoLabel;
	}

	public void setPrecioMaximoLabel(JLabel precioMaximoLabel) {
		this.precioMaximoLabel = precioMaximoLabel;
	}



	//FUNCIONALIDAD BUSQUEDA
	final ActionListener consultaAL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Prenda> prendasBusqueda = busquedaRecursiva(listaPrendas.size() - 1, listaPrendas, new ArrayList<Prenda>(listaPrendas));
			DefaultTableModel modeloTabla = (DefaultTableModel) tablaPrendas.getModel();
			modeloTabla.setRowCount(0);
			for (Prenda prenda : prendasBusqueda) {
				modeloTabla.addRow(new Object[] {prenda.getImagen(), prenda.getNombre(), prenda.getTamanyo().getNombre(),  prenda.getProveedor().getNombre(),
						prenda.getTipo().getNombre().toLowerCase(), prenda.getPrecio()});
			}
			
		}
	};
	
	final ActionListener carritoAL = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int indexSelection = tablaPrendas.getSelectedRow();
			Prenda prendaSeleccionada = new Prenda();
			String rutaArchivos = propiedades.getProperty("rutaFicheros") + "carrito.csv";
				if (indexSelection >= 0) {
					for (int i = 0; i < tablaPrendas.getColumnCount(); i++) {
						switch (i) {
						case 0: {
							prendaSeleccionada.setImagen(String.valueOf(tablaPrendas.getValueAt(indexSelection, i)));
							break;
						}
						case 1: {
							prendaSeleccionada.setNombre(String.valueOf(tablaPrendas.getValueAt(indexSelection, i)));
							break;
						}
						case 2: {
							prendaSeleccionada.setTamanyo(new Tamanyo(String.valueOf(tablaPrendas.getValueAt(indexSelection, i))));
							break;
						}
						case 3: {
							prendaSeleccionada.setProveedor(new Proveedor(String.valueOf(tablaPrendas.getValueAt(indexSelection, i))));
							break;
						}
						case 4: {
							prendaSeleccionada.setTipo(new Tipo(String.valueOf(tablaPrendas.getValueAt(indexSelection, i))));
							break;
						}
						case 5: {
							prendaSeleccionada.setPrecio((int) (tablaPrendas.getValueAt(indexSelection, i)));
							break;
						}
						default:
							prendaSeleccionada.setCantidad(1);
							break;
						}
					}
				}
				boolean isRepeated = prendaRepetida(rutaArchivos, prendaSeleccionada);
				if (!isRepeated) {
				String nuevaPrenda = HerramientasFicheros.prendaCsv(prendaSeleccionada);
				try (FileOutputStream fos = new FileOutputStream(propiedades.getProperty("rutaFicheros") + "carrito.csv", true)){
					fos.write(nuevaPrenda.getBytes());
					fos.write("\n".getBytes());
				} catch (Exception e2) {
					logger.warning("Error al escribir la prenda. " + e2.getMessage());
				}
			}
		}
	};
	
	private List<Prenda> busquedaRecursiva(int index, List<Prenda> prendas, List<Prenda> prendasBusqueda) {
		String nulo = "--Todos--";
		if (index >= 0) {
			String texto = String.valueOf(textoFiltro.getText());
			String tipo = String.valueOf(opcionTipo.getSelectedItem());
			String proveedor = String.valueOf(opcionProveedor.getSelectedItem());
			String tamanyo = String.valueOf(opcionTamanyo.getSelectedItem());
			int maxPrecio = Integer.valueOf(precioMaximo.getValue());
			
			Prenda prenda = prendas.get(index);
			
			if( texto.length() != 0 ) {
				if (!prenda.getNombre().contains(texto)) {
					prendasBusqueda.remove(prenda);
				}
			}
			
			if( tipo != nulo ) {
				if (!prenda.getTipo().toString().toLowerCase().equals(tipo)) {
					prendasBusqueda.remove(prenda);
				}
			}
			
			if( proveedor != nulo ) {
				if (!prenda.getProveedor().toString().replace("_", " ").equals(proveedor)) {
					prendasBusqueda.remove(prenda);
				}
			}
			
			if( tamanyo != nulo ) {
				if (!prenda.getTamanyo().toString().equals(tamanyo)) {
					prendasBusqueda.remove(prenda);
				}
			}
			
			if (!(prenda.getPrecio() <= maxPrecio)) {
				prendasBusqueda.remove(prenda);
			}
			
			busquedaRecursiva(index - 1, prendas, prendasBusqueda);
		}
		return prendasBusqueda;
	}
	
	final ChangeListener precioMaximoCL = new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			getPrecioMaximoLabel().setText(String.valueOf(getPrecioMaximo().getValue()) + " $");
			repaint();
		}
	};
	
	final ActionListener ordenAlfabeticoListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			List<Prenda> prendasOrdenadas = new ArrayList<Prenda>(listaPrendas);
			prendasOrdenadas.sort(comparatorPrendasAlfabetico);
			modeloTablaPrendas.setNumRows(0);
			loadTablePrendas(tablaPrendas, prendasOrdenadas);
			
		}
	};
	
	private boolean prendaRepetida(String rutaArchivo, Prenda prenda) {
		boolean isRepeated = false;
		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
			String line = ":)";
			line = br.readLine();
			while(line != null) {
				String[] strip = line.split(";");
				if (strip[0].equals(prenda.getImagen()) && strip[1].equals(prenda.getNombre())) {
					isRepeated = true;
				}
				line = br.readLine();
			}
		} catch (Exception e) {
			logger.info("La prenda no se ha podido verificar correctamente. " + e.getMessage());
			return true;
		}
		return isRepeated;
	}
	
	public String toString() {
		return "principal";
	}
	
}
