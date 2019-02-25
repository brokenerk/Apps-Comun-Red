import java.net.*;
import java.io.*;

public class SECO {
	public static void main(String[] args){
		try{
			DatagramSocket s = new DatagramSocket(1234);
			System.out.println("Servidor de datagrams iniciado, esperando datagramas...");

			for( ; ; ){
				DatagramPacket p1 = new DatagramPacket(new byte[10], 10);
				s.receive(p1);
				System.out.println("Datagrama recibido desde: " + p1.getAddress() + ": " + p1.getPort());
				String datos = new String(p1.getData(), 0, p1.getLength());
				datos = datos + " eco";
				byte[] b = datos.getBytes();
				DatagramPacket p2 = new DatagramPacket(b, b.length, p1.getAddress(), p1.getPort());
				s.send(p2);
			}//for
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class

/*
	s.receive(p);
	DataInputStream dis = new DataInputStream(new ByteArrayInputStream(p.getData()));
	ByteArrayInputStream(p.getData());
	int x = dis.readInt();
	float y = dis.readFloat();
	long z = dis.readLong();
*/