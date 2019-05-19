//ServidorControlador
import java.net.*;
import java.io.*;
import java.util.*;

public class Controlador {
	private HashMap<String, String> ubicacion;
	private HashMap<String, String[]> servidores;

	public Controlador() {
		ubicacion = new HashMap<>();
		diccionario.put("Conejo", "servidor1");
		diccionario.put("Iguana", "servidor1");
		diccionario.put("Rana", "servidor1");
		diccionario.put("Dibujo", "servidor1");
		diccionario.put("Estrella", "servidor1");
		diccionario.put("Comida", "servidor1");
		diccionario.put("Familia", "servidor1");
		diccionario.put("Conversar", "servidor1");
		diccionario.put("Computadora", "servidor1");
		diccionario.put("Sombra", "servidor1");
		diccionario.put("Navegador", "servidor1");


		ubicacion.put("Aguila", "servidor2");
		ubicacion.put("Raiz", "servidor2");
		ubicacion.put("Saltar", "servidor2");
		ubicacion.put("Ropa", "servidor2");
		ubicacion.put("Dia", "servidor2");
		ubicacion.put("Amor", "servidor2");
		ubicacion.put("Color", "servidor2");
		ubicacion.put("Libro", "servidor2");
		ubicacion.put("Lenguaje", "servidor2");
		ubicacion.put("Lapiz", "servidor2");
		ubicacion.put("Pais", "servidor2");
		ubicacion.put("Orejas", "servidor2");

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