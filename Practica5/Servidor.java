//ServidorControlador
import java.net.*;
import java.io.*;
import java.util.*;

public class Servidor
{
	protected static HashMap<String, String> ubicacion = new HashMap<>();

	public static String getServidor(String palabra) {
		if(ubicacion.containsKey(palabra))
			return ubicacion.get(palabra);
		else
			return "";
	}

	public static void main(String[] args)
	{
		ubicacion.put("Conejo", "servidor1");
		ubicacion.put("Iguana", "servidor1");

		ubicacion.put("Aguila", "servidor2");
		ubicacion.put("Raiz", "servidor2");

		try
		{
			ServerSocket s = new ServerSocket(4000);
			s.setReuseAddress(true);
			//s.setSoLinger(true, s);
			System.out.println("Servidor principal iniciado, esperando cliente...");

			for( ; ; )
			{
				Socket cl = s.accept();
				System.out.println("Cliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());

				PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
				BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));

				String palabra = br.readLine();
				String servidor = getServidor(palabra);
				
				System.out.println("Ubicacion: " + servidor);
				pw.println(servidor);
				pw.flush();
				
				br.close();
				pw.close();
				cl.close();
			}//for
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}