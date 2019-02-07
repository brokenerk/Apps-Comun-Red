import java.net.*;
import java.io.*;

// Socket bloqueante: atiende uno por uno
public class SHM {
	public static void main(String[] args) {
		try {

			ServerSocket s = new ServerSocket(9999);
			s.setReuseAddress(true);
			System.out.println("Servidor iniciado ... Esperando cliente");
			
			// No sabemos cuantos van a llegar, entonces:
			for( ; ; ) {
				// Representa la conexión, devuelve una instancia de tipo socket
				Socket cl = s.accept();
				System.out.println("Cliente conectado desde: " + cl.getInetAddress() + " " + cl.getPort());
				String msj = "Un mensaje de saludo";
				
				// getOutputStream() es un flujo orientado a byte para escribir
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
				// Usar println, lee hasta el salto de linea
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
			} // For
		}catch(Exception e) {
			e.printStackTrace();
		} // catch
	} // main
} // class