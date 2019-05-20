import java.net.*;
import java.io.*;
import java.util.*;

public class Cliente {
	private static Controlador ctrl = new Controlador();

	private static String servidorActual = "";
	private static String hostActual = "";
	private static int ptoActual = 0;

	public static String[] nombresServidores;
	public static String[][] infoServidor;

	// -----------------------------------------------------------------------
	// 							VISUALIZAR DICCIONARIO
	// -----------------------------------------------------------------------
	public static void visualizarDiccionario() {
		String[] palabras = ctrl.getPalabras();

		for(int i = 0; i < palabras.length; i++)
			Visualizacion.modelo.addElement(palabras[i]);
	}

	// -----------------------------------------------------------------------
	// 							CARGAR INFO SERVIDORES
	// -----------------------------------------------------------------------
	public static void cargarServidores() {
		HashMap<String, String[]> servidores = ctrl.getServidores();

		nombresServidores = servidores.keySet().toArray(new String[0]); //Nombres servidores
		infoServidor = servidores.values().toArray(new String[0][0]); //Hosts [0] y puertos [1]
		System.out.println("Informacion se servidores cargada\n");
	}

	// -----------------------------------------------------------------------
	// 							ASIGNAR SERVIDOR ACTUAL
	// -----------------------------------------------------------------------
	public static void asignarServidor(int i) {
		servidorActual = nombresServidores[i];
		hostActual = infoServidor[i][0];
		ptoActual = Integer.parseInt(infoServidor[i][1]);

		System.out.println("Servidor actual: " + servidorActual);
		System.out.println("Host: " + hostActual + ". Pto: " + ptoActual);
	}

	// -----------------------------------------------------------------------
	// 							BUSCAR PALABRA
	// -----------------------------------------------------------------------
	public static String buscarPalabra(String palabra) {
		String definicion = "";
		try {
			Socket cl = new Socket(hostActual, ptoActual);
			System.out.println("\nConexion establecida al " + servidorActual);

			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
			DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

			//Bandera = 0 - Buscar definicion
			dos.writeInt(0);

			System.out.println("Enviando palabra: " + palabra);
			//Enviar palabra y la intento buscar en este servidor
			dos.writeUTF(palabra);
			dos.flush();

			//Recibo respuesta del servidor actual, puede o no ser la definicion
			String respuesta = dis.readUTF();

			dos.close();
			dis.close();
			cl.close();

			definicion = respuesta;
			System.out.println("Definicion recibida: " + definicion);

			//La definicion es vacia, por lo tanto hay que buscar en otro servidor
			if(respuesta.equals("")) {
				//Intentamos hallar el servidor
				String servidorRemoto = ctrl.obtenerServidor(palabra);

				/*Si la respuesta es vacia, la palabra no esta registada en ningun 
				servidor y terminamos*/
				if(servidorRemoto.equals("")) {
					System.out.println("Palabra no encontrada en ningun servidor. Regresando cadena vacia...");
					definicion = "No encontrada";
				}
				else {
					/*Sino, nos conectamos a ese servidor, y es seguro que la palabra
					 esta ahi*/
					String hostRemoto = ctrl.obtenerHostServidor(servidorRemoto);
					int ptoRemoto = ctrl.obtenerPtoServidor(servidorRemoto);

					//Nuevo socket
					Socket cl2 = new Socket(hostRemoto, ptoRemoto);
					System.out.println("\nDefinicion no encontrada. Conectando al " + servidorRemoto);
					System.out.println("Host: " + hostRemoto + ". Puerto: " + ptoRemoto);

					DataOutputStream dos2 = new DataOutputStream(cl2.getOutputStream()); //OutputStream
					DataInputStream dis2 = new DataInputStream(cl2.getInputStream()); // InputStream

					//Bandera = 0 - Buscar definicion
					dos2.writeInt(0);

					System.out.println("Enviando palabra: " + palabra);
					//Enviar palabra y la intento buscar en otro servidor
					dos2.writeUTF(palabra);
					dos2.flush();

					//Recibo definicion del servidor
					definicion = dis2.readUTF();
					System.out.println("Definicion recibida: " + definicion);

					dos2.close();
					dis2.close();
					cl2.close();
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}//catch
		return definicion;
	}

	// -----------------------------------------------------------------------
	// 							CONSULTAR DESDE VISUALIZACION
	// -----------------------------------------------------------------------
	public static void consultarDesdeVisualizacion(String palabra) {
		String definicion = buscarPalabra(palabra);

		Diccionario.tfPalabra.setText("");
		Diccionario.tfPalabra.setText(palabra);

		Diccionario.taDefinicion.setText("");
		Diccionario.taDefinicion.setText(definicion);
	}

	// -----------------------------------------------------------------------
	// 							AGREGAR PALABRA
	// -----------------------------------------------------------------------
	public static boolean agregarPalabra(String palabra, String definicion) {
		boolean success = false;

		try {
			if(ctrl.existePalabra(palabra) == false) {
				Socket cl = new Socket(hostActual, ptoActual);
				System.out.println("\nConexion establecida al " + servidorActual);

				DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
				DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

				// Bandera = 1 - Agregar palabra
				dos.writeInt(1);

				System.out.println("Enviando palabra: " + palabra);
				// Enviar palabra y la agrega 
				dos.writeUTF(palabra);
				dos.flush();

				System.out.println("Enviando definicion: " + definicion);
				// Enviar definicion
				dos.writeUTF(definicion);
				dos.flush();

				dos.close();
				dis.close();
				cl.close();

				//Registrando palabra en el Controlador 
				ctrl.agregarPalabra(palabra, servidorActual);
				success = true;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}//catch
		return success;
	}
}