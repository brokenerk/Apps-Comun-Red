import java.net.*;
import java.io.*;
import java.util.*;

public class Servidor1
{
	protected static HashMap<String, String> diccionario = new HashMap<>();

	public static void main(String[] args)
	{
		diccionario.put("Conejo", "Mamífero de cuerpo alargado y arqueado de unos 40 cm de longitud, pelo suave y espeso, orejas largas, cola corta y patas traseras más desarrolladas que las delanteras");
		diccionario.put("Iguana", "Reptil escamoso americano que puede alcanzar hasta 1,80 m de longitud, con la lengua no protráctil y los dientes sobre la superficie interna de las mandíbulas");

		try
		{
			ServerSocket s = new ServerSocket(4001);
			s.setReuseAddress(true);
			System.out.println("Servidor1 iniciado, esperando cliente...");

			for( ; ; )
			{
				Socket cl = s.accept();
				System.out.println("Cliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
				
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
				BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));

				String palabra = br.readLine();
				pw.println(diccionario.get(palabra));
				pw.flush();

				br.close();
				pw.close();
				cl.close();
				
			}//for
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}