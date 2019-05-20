import java.net.*;
import java.io.*;
import java.util.*;

public class Servidor1 {
	private static HashMap<String, String> diccionario = new HashMap<>();

    // -----------------------------------------------------------------------
	// 							AGREGAR NUEVA DEFINICION
	// -----------------------------------------------------------------------
	public static void agregarDefinicion(String palabra, String definicion) {
		diccionario.put(palabra, definicion);
	}

	// -----------------------------------------------------------------------
	// 							BUSCAR DEFINICION
	// -----------------------------------------------------------------------
	public static void buscarDefinicion(String palabra, DataOutputStream dos) {
		String respuesta = "";
		System.out.println("Buscando definicion para: " + palabra);

		if(diccionario.containsKey(palabra))
			respuesta = diccionario.get(palabra);

		try {
			System.out.println("Definicion encontrada: " + respuesta);
			dos.writeUTF(respuesta);
			dos.flush();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------
	// 							MAIN
	// -----------------------------------------------------------------------
	public static void main(String[] args) {
		// Definiciones
		diccionario.put("Conejo", "Mamifero de cuerpo alargado y arqueado de unos 40 cm de longitud, pelo suave y espeso, orejas largas, cola corta y patas traseras mas desarrolladas que las delanteras.");
		diccionario.put("Iguana", "Reptil escamoso americano que puede alcanzar hasta 1,80 m de longitud, con la lengua no protractil y los dientes sobre la superficie interna de las mandibulas.");
		diccionario.put("Rana", "Anfibio sin cola, de piel lisa y brillante, tronco rechoncho, cabeza grande y ojos saltones, con las extremidades posteriores muy desarrolladas para saltar.");
		diccionario.put("Dibujo", "Forma que resulta de combinarse las lineas, figuras y otros elementos que adornan una cosa o que son constitutivos de ella.");
		diccionario.put("Estrella", "Astro o cuerpo celeste que brilla con luz propia en el firmamento.");
		diccionario.put("Comida", "Sustancia sólida que se come y sirve de alimento.");
		diccionario.put("Familia", "Grupo de personas formado por una pareja (normalmente unida por lazos legales o religiosos), que convive y tiene un proyecto de vida en comun, y sus hijos, cuando los tienen.");
		diccionario.put("Conversar", "Hablar [una persona] con otra sobre algo alternando los turnos de palabra.");
		diccionario.put("Computadora", "Maquina electronica capaz de almacenar informacion y tratarla automaticamente mediante operaciones matematicas y logicas controladas por programas informaticos.");
		diccionario.put("Sombra", "Imagen oscura que proyecta un cuerpo opaco sobre una superficie al interceptar los rayos de luz.");
		diccionario.put("Navegador", "Programa que permite navegar por internet u otra red informatica de comunicaciones.");

		try {
			ServerSocket s = new ServerSocket(4001);
			s.setReuseAddress(true);
			System.out.println("Servidor1 iniciado, esperando cliente...");

			for( ; ; ) {
				Socket cl = s.accept();
				System.out.println("\nCliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
				
				DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
				DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

				int bandera = dis.readInt();

				if(bandera == 0) {
					// Buscar definicion
					String palabra = dis.readUTF();
					buscarDefinicion(palabra, dos);
				}
				else if(bandera == 1) {
					// Agregar definiciones
					String palabra = dis.readUTF();
					String definicion = dis.readUTF();
					agregarDefinicion(palabra, definicion);
				}

				dis.close();
				dos.close();
				cl.close();	
			}//for
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}