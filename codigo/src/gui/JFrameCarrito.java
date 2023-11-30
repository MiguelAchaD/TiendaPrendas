package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import domain.Prenda;
import domain.Proveedor;
import domain.Tamanyo;
import domain.Tipo;
import domain.Usuario;

public class JFrameCarrito extends JFrame {
	private static final long serialVersionUID = 1L;

	private Usuario usuario;
	private List<Prenda> prendas;
	private JPanelNavegacion panelNavegacion;

	public JFrameCarrito(Usuario usuario) {
		// CONFIGURACION DEL USUARIO
		this.usuario = new Usuario(usuario);
		// CONFIGURACION GENERAL
		JFrameHerramientas herramientas = new JFrameHerramientas();
		herramientas.configurarVentanaGeneralNormal(this);

		// CONFIGURACION DE VENTANA
		// JBUTTONs/JLABELs/JTABLEs/...
		// PANEL NAVEGACION
		panelNavegacion = new JPanelNavegacion(usuario, this);
		// PANEL
		List<Prenda> prendasLeer = leerCsv("carrito.csv");

		for (Prenda prenda : prendasLeer) {
			System.out.println(prenda);
		}
		
		List<Prenda> prendasEscribir = new ArrayList<>();
        escribirCsv("carrito.csv", prendasEscribir);

		// LAYOUT
		this.getContentPane().setLayout(new GridBagLayout());

		GridBagConstraints gbcPanelNavegacion = new GridBagConstraints();
		gbcPanelNavegacion.gridx = 0;
		gbcPanelNavegacion.gridy = 0;
		gbcPanelNavegacion.weightx = 0;
		gbcPanelNavegacion.weighty = 0;
		gbcPanelNavegacion.fill = GridBagConstraints.BOTH;

		// JPANELs
		panelNavegacion.setBackground(new Color(35, 39, 42));

		panelNavegacion.setPreferredSize(new Dimension(1500, 70));
		panelNavegacion.setMinimumSize(panelNavegacion.getPreferredSize());

		panelNavegacion.setBackground(panelNavegacion.getBackground());
		panelNavegacion.getPanelLogo().setBackground(panelNavegacion.getBackground());
		panelNavegacion.getPanelLinks().setBackground(panelNavegacion.getBackground());

	}

	public static void escribirCsv(String rutaArchivo, List<Prenda> prendas) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (Prenda prenda : prendas) {
                String linea = prendaCsv(prenda);
                bw.write(linea);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String prendaCsv(Prenda prenda) {
        StringBuilder sb = new StringBuilder();
        sb.append(prenda.getNombre()).append(";");
        sb.append(prenda.getProveedor().getNombre()).append(";");
        sb.append(prenda.getTipo().getNombre()).append(";");
        sb.append(prenda.getTamanyo().getNombre());
        return sb.toString();
    }

	
	public static List<Prenda> leerCsv(String rutaArchivo) {
		List<Prenda> prendas = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
			String linea;
			while ((linea = br.readLine()) != null) {
				Prenda prenda = csvPrenda(linea);
				if (prenda != null) {
					prendas.add(prenda);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prendas;
	}

	private static Prenda csvPrenda(String linea) {
		String[] atributos = linea.split(";");
		if (atributos.length == 4) {
			try {
				String nombre = atributos[0].trim();
				Proveedor proveedor = new Proveedor(atributos[1].trim());
				Tipo tipo = new Tipo(atributos[2].trim());
				Tamanyo tamanyo = new Tamanyo(atributos[3].trim());
				return new Prenda(tamanyo, tipo, proveedor, nombre, "", 0);
			} catch (IllegalArgumentException e) {
				System.err.println("Error al construir la prenda desde la línea: " + linea);
			}
		} else {
			System.err.println("Formato incorrecto en la línea: " + linea);
		}

		return null;
	}
}
