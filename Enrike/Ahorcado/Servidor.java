import java.net.*;
import java.io.*;

public class Servidor{
	public static String[] facil = {"carro", "perro", "gato", "coco", "casa", "roca"};
	public static String[] media = {"ferrocarril", "administrativo", "disciplina", "colocacion", "medicina", "electronico"};
	public static String[] dificil = {"sockets de datagrama", "protocolo udp", "aplicaciones de red", "ingenieria en sistemas", "escuela de computo", "cliente - servidor"};

	public static void main(String[] args){
		try{
			//Creamos socket datagrama
			DatagramSocket s = new DatagramSocket(1234);
			System.out.println("");
			System.out.println("Servidor de datagrams iniciado. Ahorcado... esperando datagramas");

			for( ; ; ){
				//Leemos el datagrama recibido del cliente
				DatagramPacket p = new DatagramPacket(new byte[65535], 65535);
				s.receive(p);

				System.out.println("");
				System.out.println("Datagrama recibido desde: " + p.getAddress() + ": " + p.getPort() + ":");
				//Convertimos los bytes[] a integer
				int dificultad = Integer.parseInt(new String(p.getData(), 0, p.getLength()));
				
				//Generamos un aleatorio para elegir una palabra a enviar
				int ran = (int) (Math.random() * 6);
				String palabra = "";

				if(dificultad == 0){
					System.out.print("Dificultad facil. Enviando palabra... ");
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

				//Envia la palabra como bytes[] al cliente
				byte[] b = palabra.getBytes();
				DatagramPacket p2 = new DatagramPacket(b, b.length, p.getAddress(), p.getPort());
				s.send(p2);

			}//for
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class