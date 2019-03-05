import java.net.*;
import java.io.*;

public class Servidor{
	public static String[] facil = {"carro", "perro", "gato"};
	public static String[] media = {"ferrocarril", "administrativo", "disciplina"};
	public static String[] dificil = {"sockets de datagrama", "hola mundo feliz", "aplicaciones de red"};

	public static void main(String[] args){
		try{
			DatagramSocket s = new DatagramSocket(1234);
			System.out.println("Servidor de datagrams iniciado. Ahorcado... esperando datagramas");

			for( ; ; ){
				DatagramPacket p = new DatagramPacket(new byte[65535], 65535);
				s.receive(p);
				System.out.println("Datagrama recibido desde: " + p.getAddress() + ": " + p.getPort() + " con la dificultad:");
				int dificultad = Integer.parseInt(new String(p.getData(), 0, p.getLength()));
				
				int ran = (int) (Math.random() * 3);
				String palabra = "";

				if(dificultad == 0){
					System.out.print("Dificultad fácil. Enviando palabra... ");
					palabra = facil[ran];
				}
				else if(dificultad == 1){
					System.out.print("Dificultad media. Enviando palabra... ");
					palabra = media[ran];
				}
				else{
					System.out.print("Dificultad dificil. Enviando palabra... ");
					palabra = dificil[ran];
				}

				System.out.println(palabra);
				byte[] b = palabra.getBytes();
				DatagramPacket p2 = new DatagramPacket(b, b.length, p.getAddress(), p.getPort());
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