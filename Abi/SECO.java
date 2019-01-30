// 29 de ENero 2019
// Primero correr aplicación cliente
// SErvidor
import java.net.*;
import java.io.*;

public class SECO {
	public static void main(String [] args) {
		try {
			ServerSocket s = new ServerSocket(8888);
			//s.setReuseAddress(true);
			//s.setSoLinger(true, s);
			System.out.printl("Servicio de eco iniciado... esperando cliente...");

			// Como no sabemos cuantos van a llegar, entoces
			// Como es socket bloqueante solo atiende uno por uno
			for(;;) {
				// Devuelve una instancia de tipo socket, representa la conexión
				Socket cl = s.accept();
				// Desde que dirección y que puerto se conecto el cliente
				System.out.println("Cliente conectado " + cl.getInetAddresss() + ":" + cl.getPort());
				// Asocio flujo de escritura y lectura
				// Siempre declarar el de escritura
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
				BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
				// Como no sabemos cuantos mensajes va a mandar
				for(;;) {
					String msj = br.readLine();
					// Si es la cadena especial para cerrar el ciclo
					if(msj.compareToIgnoreCase("salir") == 0) {
						System.out.println("Cliente se va");
						br.close();
						pw.close();
						cl.close();
						break;
					}
					else {
						String eco = msj + " eco";
						pw.println(eco);
						pw.flush();
					}	
				} // Fin for
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

/* 
	Para el cliente
	Asociar flujo de lectura
	Validar mensaje de salida
	Imprimir
*/