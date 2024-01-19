package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import domain.Prenda;
import domain.Proveedor;
import domain.Tamanyo;
import domain.Tipo;

public class HerramientasFicheros {
	private Properties properties;
	private static Logger logger;
	
	public HerramientasFicheros() throws IOException {
		properties = lectorPropiedades("conf/config.properties");
		logger = Logger.getLogger("HerramientasFicheros");
	}
	
	public static void escribirCsv(String rutaArchivo, List<Prenda> prendas) {
        try (FileOutputStream fos = new FileOutputStream(rutaArchivo, true)) {
            for (Prenda prenda : prendas) {
                String linea = prendaCsv(prenda);
                fos.write(linea.getBytes());
                fos.write("\n".getBytes());
            }
        } catch (IOException e) {
        	logger.warning("Error al escribir la prenda. " + e.getMessage());
        }
    }

    public static String prendaCsv(Prenda prenda) {
        StringBuilder sb = new StringBuilder();
		
        if (prenda.getNombre() != "" && prenda.getImagen() != "" && prenda.getPrecio() != 0 && prenda.getProveedor().getNombre() != "" && prenda.getTamanyo().getNombre() != "" && prenda.getTipo().getNombre() != "") {
	        sb.append(prenda.getImagen()).append(";");
	        sb.append(prenda.getNombre()).append(";");
	        sb.append(prenda.getTamanyo().getNombre()).append(";");
	        sb.append(prenda.getProveedor().getNombre()).append(";");
	        sb.append(prenda.getTipo().getNombre()).append(";");
	        sb.append(prenda.getPrecio()).append(";");
	        sb.append(prenda.getCantidad());
    	}	
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
			logger.warning("Error al leer la prenda. " + e.getMessage());
		}

		return prendas;
	}
	
	private static Prenda csvPrenda(String linea) {
		String[] atributos = linea.split(";");
		if (atributos.length == 7) {
			try {
				String imagen = atributos[0].trim();
				String nombre = atributos[1].trim();
				Tamanyo tamanyo = new Tamanyo(atributos[2].trim());
				Proveedor proveedor = new Proveedor(atributos[3].trim());
				Tipo tipo = new Tipo(atributos[4].trim());
				int precio = Integer.parseInt(atributos[5]);
				int cantidad = Integer.parseInt(atributos[6]);
				return new Prenda(tamanyo, tipo, proveedor, nombre, imagen, precio, cantidad);
			} catch (IllegalArgumentException e) {
				logger.warning("Error al construir la prenda. " + e.getMessage());
			}
		} else {
			logger.warning("Formato incorrecto al construir la prenda.");
		}
		
		return null;
	}
	
	public Properties lectorPropiedades(String archivo) {
        properties = new Properties();
        try (InputStream input = new FileInputStream(archivo)) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
	
    static class Formateador extends java.util.logging.Formatter {
        @Override
        public String format(java.util.logging.LogRecord info) {
            return info.getLevel() + ": " + info.getMessage() + " -> " + info.getSourceClassName() + "\n";
        }
    }
	
	public void configurarLogger() throws IOException {
        Handler fileHandler = new FileHandler(properties.getProperty("rutaLogger") + "logger.log");
        fileHandler.setFormatter(new Formateador());
        logger.setLevel(Level.ALL);
        fileHandler.setLevel(Level.ALL);
        
        logger.addHandler(fileHandler);
    }

	public static Logger getLogger() {
		return logger;
	}
	
	
}
