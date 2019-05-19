//ServidorControlador
import java.net.*;
import java.io.*;
import java.util.*;

public class Controlador {
	private HashMap<String, String> ubicacion;
	private HashMap<String, String[]> servidores;

	public Controlador() {
		ubicacion = new HashMap<>();
		ubicacion.put("Conejo", "servidor1");
		ubicacion.put("Iguana", "servidor1");

		ubicacion.put("Aguila", "servidor2");
		ubicacion.put("Raiz", "servidor2");

		servidores = new HashMap<>();

		String[] servidor1 = new String[]{"127.0.0.1", "4001"};
		servidores.put("servidor1", servidor1);

		String[] servidor2 = new String[]{"127.0.0.1", "4002"};
		servidores.put("servidor2", servidor2);	
	}

	public HashMap<String, String[]> getServidores() {
		return servidores;
	}

	public boolean existePalabra(String palabra){
		return ubicacion.containsKey(palabra);
	}

	public void agregarPalabra(String palabra, String servidor) {
		ubicacion.put(palabra, servidor);
	}

	public String obtenerServidor(String palabra) {
		if(ubicacion.containsKey(palabra))
			return ubicacion.get(palabra);
		else
			return "";
	}

	public String obtenerHostServidor(String servidor) {
		return servidores.get(servidor)[0];
	}

	public int obtenerPtoServidor(String servidor) {
		return Integer.parseInt(servidores.get(servidor)[1]);
	}
}