import java.net.*;
import java.io.*;

public class SECO_O{
	public static void main(String[] args){
		try{
			DatagramSocket s = new DatagramSocket(1234);
			System.out.println("Servidor de datagrams iniciado, esperando datagramas...");

			for( ; ; ){
				DatagramPacket p = new DatagramPacket(new byte[5], 5);
				s.receive(p);
				String datos = new String(p.getData(), 0, p.getLength());
				ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(datos.getBytes()));
				Datos d1 = (Datos) ois.readObject();
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