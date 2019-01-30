// 29 de ENero 2019
import java.net.*;
import java.io.*;

public class SHM {
	public static void main(String [] args) {
		try {
			ServerSocket s = new ServerSocket(9999);
			s.setReuseAddress(true);
			s.setSoLinger(true, s);
			System.out.printl("Servicio iniciado... esperando cliente...");

			// Como no sabemos cuantos van a llegar, entoces
			// Como es socket bloqueante solo atiende uno por uno
			for(;;) {
				// Devuelve una instancia de tipo socket, representa la conexión
				Socket cl = s.accept();
				// Desde que dirección y que puerto se conecto el cliente
				System.out.println("Cliente conectado " + cl.getInetAddresss() + ":" + cl.getPort());
				String msj = "Un mensaje de saludo";
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
				// Usar println, lee hasta el salto de linea.
				pw.println(msj);
				//pw.close();
				/*
					Puede que llegue o no, depende de pw.println(msj); la aplicación tiene asociado
					dos buffers, los datos se quedan encolados, no se van directamente a la red.
					Si escribimos y luego luego cerramos, el canal de comunicación quiza mueree.
				*/
				// Agregamos el metodo para que jale todo lo que tiene en el buffer
				pw.flush();
				pw.close();
				cl.close();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

