import java.net.*;
import java.io.*;

public class CHM
{
	public static void main(String[] args)
	{
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("\nEscribe la direccion del servidor: ");
			//Desde CMD: ipconfig /all
			String host = br.readLine();
			int pto = 9999;
			Socket cl = new Socket(host, pto);
			System.out.println("Conexion establecida, recibiendo datos...");

			BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));
			String msj = br2.readLine();
			System.out.println("\nMensaje recibido: " + msj);
			br2.close();
			br.close();
			cl.close();

		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}