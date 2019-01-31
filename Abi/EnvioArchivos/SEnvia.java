import java.net.*;
import java.io.*;

public class SEnvia {
	public static void main(String[] args) {
		try {

			ServerSocket s = new ServerSocket(8888);
			s.setReuseAddress(true);
			System.out.println("Servidor ECO iniciado ... Esperando cliente...");
			
			for( ; ; ) {
				Socket cl = s.accept();
				// Desde que dirección y que puerto se conecto el cliente
				System.out.println("Cliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
				
				DataInputStream dis = new DataInputStream(cl.getInputStream());
				String nombre = dis.readUTF();
				long tam = dis.readLong();
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));

				long recibidos = 0;
				int n = 0, porciento = 0;
				byte[]b = new byte[2000];
				
				while(recibidos < tam) {
					n = dis.read(b);
					// De donde saca los datos, cuantos datos se va a saltar, lo que se pudo leer
					dos.write(b, 0, n);
					dos.flush();
					recibidos += n;
					porciento = (int)((recibidos * 100) / tam);
					System.out.print("\r Recibiendo el " + porciento + "%"); 
				} // while

				dis.close();
				dos.close();
				cl.close();
			}

		} // try
		catch(Exception e) {
			e.printStackTrace();
		} // catch
	} // main
} // Class