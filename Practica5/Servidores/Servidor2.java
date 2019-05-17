import java.net.*;
import java.io.*;
import java.util.*;

public class Servidor2 {
	protected static HashMap<String, String> diccionario = new HashMap<>();

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
		diccionario.put("Aguila", "Ave rapaz falconiforme, de aproximadamente 2 m de envergadura, con vista muy aguda.");
		diccionario.put("Raiz", "Organo de las plantas que crece hacia el interior de la tierra, por el que se fijan al suelo y absorben las sustancias necesarias para su crecimiento.");

		try {
			ServerSocket s = new ServerSocket(4002);
			s.setReuseAddress(true);
			System.out.println("Servidor2 iniciado, esperando cliente...");

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