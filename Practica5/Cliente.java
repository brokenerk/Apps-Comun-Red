import java.net.*;
import java.io.*;

public class Cliente
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String host = "127.0.0.1";
			int pto = 4000; //Servidor Controlador

			Socket cl = new Socket(host, pto);
			System.out.println("Conexion establecida, escribe una palabra...");

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
			BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));

			String palabra = br.readLine();
			pw.println(palabra);
			pw.flush();

			String servidor = br2.readLine();
			System.out.println("Ubicacion " + palabra + ": " + servidor);

			br.close();
			br2.close();
			pw.close();
			cl.close();

			int nvoPto = -1;

			if(servidor.equals("servidor1")) {
				nvoPto = 4001;
			}
			else if(servidor.equals("servidor2")) { 
				nvoPto = 4002;
			}

			Socket cl2 = new Socket(host, nvoPto);
			System.out.println("Conectandose al " + servidor);

			PrintWriter pw2 = new PrintWriter(new OutputStreamWriter(cl2.getOutputStream()));
			BufferedReader br3 = new BufferedReader(new InputStreamReader(cl2.getInputStream()));

			pw2.println(palabra);
			pw2.flush();

			String definicion = br3.readLine();
			System.out.println("Defincion " + palabra + ": " + definicion);

			br3.close();
			pw2.close();
			cl2.close();

		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}