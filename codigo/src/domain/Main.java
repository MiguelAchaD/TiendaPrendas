package domain;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import gui.JFrameCarga;
import gui.JFramePrincipal;


public class Main {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//APERTURA DE LA BD
		DBmanager dbm = new DBmanager();
		dbm.initConexion();
		

		//DECLARACION DE VENTANAS
		JFrameCarga carga;
		JFramePrincipal principal;
		
		//CARGA PRINCIPAL
		List<String> ventanas = new ArrayList<>();
		HashMap<String, List<String>> valoresVentanas = new HashMap<>();
		carga = new JFrameCarga();
		
		//INICIAR BUSQUEDA
		principal = new JFramePrincipal();
		for (Tienda.Proveedor elemento : Tienda.Proveedor.values()) {
			principal.getOpcionProveedor().addItem(elemento.toString().replace("_", " "));
		}
		
		for (Prenda.Tipo elemento : Prenda.Tipo.values()) {
			principal.getOpcionTipo().addItem(elemento.toString().toLowerCase());
		}
		
		//INICIAR PRENDAS
		principal.setModeloTablaPrendas(new DefaultTableModel());
		DefaultTableCellRenderer tr = new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				Component resultado = new Component(){
					private static final long serialVersionUID = 1L;
				};
				JLabel resultado1 = new JLabel();
				JButton resultado2 = new JButton();
				if (column == 0) {
					resultado1.setIcon(new ImageIcon((new ImageIcon(value.toString()).getImage().getScaledInstance(300, 300, Image.SCALE_DEFAULT))));
					resultado1.setHorizontalAlignment(JLabel.CENTER);
					resultado = resultado1;
				} 
				
				if (column == 1) {
					resultado1.setText(value.toString());
					resultado1.setHorizontalAlignment(JLabel.CENTER);
					resultado = resultado1;
				}
				
				if (column == 2) {
					resultado1.setText(value.toString().replace("_", " "));
					resultado1.setHorizontalAlignment(JLabel.CENTER);
					resultado = resultado1;
				}
				
				if (column == 3) {
					resultado1.setText(value.toString().toLowerCase());
					resultado1.setHorizontalAlignment(JLabel.CENTER);
					resultado = resultado1;
				}
				
				if (column == 4) {
					resultado1.setText(value.toString());
					resultado1.setHorizontalAlignment(JLabel.CENTER);
					resultado = resultado1;
				}
				
				if (column == 5) {
					resultado1.setText(value.toString() + " $");
					resultado1.setHorizontalAlignment(JLabel.CENTER);
					resultado = resultado1;
				}
				
				if (column == 6) {
					//TODO: Intentar Añadir Frame
					resultado2.setText("Añadir a Carrito");
//					resultado2.setPreferredSize(new Dimension(30, 20));
					resultado2.setBackground(Color.white);
					resultado2.setBorder(BorderFactory.createEmptyBorder());
					resultado2.setHorizontalAlignment(JLabel.CENTER);
					resultado = resultado2;
				}
				
				return resultado;
			}
		};
		
		
		
//		principal.getTablaPrendas().getColumnModel().getColumn(0).setCellRenderer( centerRenderer );
		tr.setHorizontalAlignment(JLabel.CENTER);
		principal.getTablaPrendas().setDefaultRenderer(Object.class, tr);
		principal.getTablaPrendas().setModel(principal.getModeloTablaPrendas());
		DefaultTableModel modeloTabla = principal.getModeloTablaPrendas();

		JTable tablaPrendas = principal.getTablaPrendas();
		tablaPrendas.setDefaultEditor(Object.class, null);
		
		modeloTabla.addColumn(new Object());
		modeloTabla.addColumn(new Object());
		modeloTabla.addColumn(new Object());
		modeloTabla.addColumn(new Object());
		modeloTabla.addColumn(new Object());
		modeloTabla.addColumn(new Object());
		modeloTabla.addColumn(new Object());
		
		tablaPrendas.getColumnModel().getColumn(0).setPreferredWidth(400);
		tablaPrendas.getColumnModel().getColumn(1).setPreferredWidth(200);
		tablaPrendas.getColumnModel().getColumn(2).setPreferredWidth(100);
		tablaPrendas.getColumnModel().getColumn(3).setPreferredWidth(50);
		tablaPrendas.getColumnModel().getColumn(4).setPreferredWidth(50);
		tablaPrendas.getColumnModel().getColumn(5).setPreferredWidth(50);
		tablaPrendas.setRowHeight(400);
		
		List<Prenda> prendas = dbm.getPrendas();
		for (Prenda prenda : prendas) {
			modeloTabla.addRow(new Object[] {prenda.getImagen(), prenda.getNombre(), prenda.getProveedor(), prenda.getTipo(), prenda.getTamanyo(), prenda.getPrecio(), new JButton("Añadir al carrito")});
		}
		
		
		
//		ventanas.add(principal.toString());
//		
//		//CARGA DINAMICA DE VENTANAS CON INFORMACION
//		int tamanyoVentanas = ventanas.size();
//		while (true) {
//			if (tamanyoVentanas > 0 && ventanas.size() != tamanyoVentanas) {
//				tamanyoVentanas = ventanas.size();
//				
//				if(tamanyoVentanas > 1) {
//					
////					eval(ventanas.get(ventanas.size()-1));
//				}
//			}
//		}
		dbm.cerrarConexion();
	}
	
}
