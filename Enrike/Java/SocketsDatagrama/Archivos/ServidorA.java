import java.net.*;
import java.io.*;

public class ServidorA{
	public static void main(String[] args){
		try{
			DatagramSocket s = new DatagramSocket(1234);
			System.out.println("Servidor de datagrams iniciado, esperando datagramas...");

			for( ; ; ){
				DatagramPacket p = new DatagramPacket(new byte[60000], 60000);
				s.receive(p);
				System.out.println("Datagrama recibido desde: " + p.getAddress() + ": " + p.getPort());
				
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(p.getData()));
				Archivo d1 = (Archivo) ois.readObject();
				System.out.println("Recibo archivo: " + d1.getNombre() + ". Parte " + d1.getParte() + "/" + d1.getTotal());
			}//for
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
