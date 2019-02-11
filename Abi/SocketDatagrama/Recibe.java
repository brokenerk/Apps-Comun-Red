import java.net.*;
import java.io.*;
public class Recibe {
	public static void main(String[] args) {
		try {
			DatagramSocket s = new DatagramSocket(1234);
			System.out.println("Servicio de datagrama iniciado... esperando datagramas");
			for(;;) {
				// 65535 por el UDP
				DatagramPacket p = new DatagramPacket(new byte [65535], 65535);
				s.receive(p); // Se bloquea el servidor
				System.out.println("Datagrama recibido desde " + p.getAddress() + " : " + p.getPort() + " con los datos:");
				// Evita desperdiciar espacioss
				String datos = new String(p.getData(), 0, p.getLength());
			} // for
		}		
		catch(Exception e) {
			e.printStackTrace();
		} // catch
	} // main
}