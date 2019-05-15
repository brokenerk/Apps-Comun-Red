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
		diccionario.put("Conejo", "Mamifero de cuerpo alargado y arqueado de unos 40 cm de longitud, pelo suave y espeso, orejas largas, cola corta y patas traseras mas desarrolladas que las delanteras.");
		diccionario.put("Iguana", "Reptil escamoso americano que puede alcanzar hasta 1,80 m de longitud, con la lengua no protractil y los dientes sobre la superficie interna de las mandibulas.");

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
					//Buscar definicion
					String palabra = dis.readUTF();
					buscarDefinicion(palabra, dos);
				}
				else if(bandera == 1) {
					//Agregar definiciones
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