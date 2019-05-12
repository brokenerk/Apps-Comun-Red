import java.net.*;
import java.io.*;
import java.util.*;

public class Servidor2
{
	protected static HashMap<String, String> diccionario = new HashMap<>();

	public static void agregarDefinicion(String palabra, String definicion){
		diccionario.put(palabra, definicion);
	}

	public static void main(String[] args)
	{
		diccionario.put("Aguila", "Ave rapaz falconiforme, de aproximadamente 2 m de envergadura, con vista muy aguda.");
		diccionario.put("Raiz", "Organo de las plantas que crece hacia el interior de la tierra, por el que se fijan al suelo y absorben las sustancias necesarias para su crecimiento.");

		try
		{
			ServerSocket s = new ServerSocket(4002);
			s.setReuseAddress(true);
			System.out.println("Servidor2 iniciado, esperando cliente...");

			for( ; ; )
			{
				Socket cl = s.accept();
				System.out.println("Cliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
				
				DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
				DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

				String palabra = dis.readUTF();

				String respuesta = "";

				if(diccionario.containsKey(palabra))
					respuesta = diccionario.get(palabra);

				dos.writeUTF(respuesta);
				dos.flush();
				
				dis.close();
				dos.close();
				cl.close();
				
			}//for
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}