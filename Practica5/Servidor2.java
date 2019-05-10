import java.net.*;
import java.io.*;
import java.util.*;

public class Servidor2
{
	protected static HashMap<String, String> diccionario = new HashMap<>();

	public static void main(String[] args)
	{
		diccionario.put("Aguila", "Ave rapaz falconiforme, de aproximadamente 2 m de envergadura, con vista muy aguda");
		diccionario.put("Raiz", "Órgano de las plantas que crece hacia el interior de la tierra, por el que se fijan al suelo y absorben las sustancias necesarias para su crecimiento");

		try
		{
			ServerSocket s = new ServerSocket(4002);
			s.setReuseAddress(true);
			System.out.println("Servidor2 iniciado, esperando cliente...");

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