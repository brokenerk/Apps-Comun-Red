import java.net.*;
import java.io.*;

public class Recibe{
	public String[] facil = {"carro", "perro", "gato"};
	public String[] medio = {"ferrocarril", "administrativo", "disciplina"};
	public String[] dificil = {"sockets de datagrama", "hola mundo feliz", "aplicaciones de red"};

	public static void main(String[] args){
		try{
			DatagramSocket s = new DatagramSocket(1234);
			System.out.println("Servidor de datagrams iniciado, esperando datagramas...");

			for( ; ; ){
				DatagramPacket p = new DatagramPacket(new byte[65535], 65535);
				s.receive(p);
				System.out.println("Datagrama recibido desde: " + p.getAddress() + ": " + p.getPort() + " con los datos:");
				String datos = new String(p.getData(), 0, p.getLength());
				System.out.println("Dato: " + datos);
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