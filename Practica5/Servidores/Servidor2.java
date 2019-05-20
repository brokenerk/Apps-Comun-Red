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
		
		// Definiciones
		diccionario.put("Aguila", "Ave rapaz falconiforme, de aproximadamente 2 m de envergadura, con vista muy aguda.");
		diccionario.put("Raiz", "Organo de las plantas que crece hacia el interior de la tierra, por el que se fijan al suelo y absorben las sustancias necesarias para su crecimiento.");
		diccionario.put("Saltar", "Elevarse del suelo u otra superficie con impulso para caer en el mismo lugar o en otro.");
		diccionario.put("Ropa", "Nombre generico de cualquier pieza de tela confeccionada que viste a una persona, un objeto o un lugar.");
		diccionario.put("Dia", "Tiempo que emplea la Tierra en dar una vuelta sobre si misma, equivalente a 24 horas, y que se utiliza como unidad de tiempo; se cuenta normalmente desde las doce de la noche hasta veinticuatro horas despues.");
		diccionario.put("Amor", "Sentimiento de vivo afecto e inclinacion hacia una persona o cosa a la que se le desea todo lo bueno.");
		diccionario.put("Color", "Impresion que producen en la retina los rayos de luz reflejados y absorbidos por un cuerpo, segun la longitud de onda de estos rayos.");
		diccionario.put("Libro", "Conjunto de hojas de papel, pergamino, vitela, etc., manuscritas o impresas, unidas por uno de sus lados y normalmente encuadernadas, formando un solo volumen.");
		diccionario.put("Lenguaje", "Capacidad propia del ser humano para expresar pensamientos y sentimientos por medio de la palabra.");
		diccionario.put("Lapiz", "Utensilio para escribir, dibujar o pintar que consiste en una barra delgada y larga generalmente de madera, con una mina cilindrica fina de grafito u otra sustancia mineral en el interior que sobresale por uno de los extremos de esta barra cuando esta afilado.");
		diccionario.put("Pais", "Comunidad social con una organizacion politica comun y un territorio.");
		diccionario.put("Oreja", "Parte externa del oido del hombre y otros mamiferos, formada por un repliegue cutaneo sostenido por una lamina cartilaginosa.");
		
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