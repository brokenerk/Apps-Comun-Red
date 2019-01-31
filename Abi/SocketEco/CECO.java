import java.net.*;
import java.io.*;

public class CECO
{
	public static void main(String[] args)
	{
		try
		{
			// Asocio un flujo de lectura
			// InputStreamreader es como un puente, así puedo leer parrafos 
			// Convierte un flujo orientado a byte a caracter
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("\nEscribe la direccion del servidor eco: ");
			
			String host = br.readLine();
			int pto = 8888;
			Socket cl = new Socket(host, pto);
			System.out.println("Conexion establecida, recibiendo datos...");

			PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
			BufferedReader br2 = new BufferedReader(new InputStreamReader(cl.getInputStream()));

			for( ; ; )
			{
				System.out.println("\nNuevo mensaje: ");
				String msj = br.readLine();
				pw.println(msj);
				pw.flush();

				if(msj.compareToIgnoreCase("salir") == 0)
				{
					break;
				}
				else
				{
					String respuesta = br2.readLine();
					System.out.println("Respuesta: " + respuesta);
					//continue
				}//else
			}//for

			br.close();
			br2.close();
			pw.close();
			cl.close();

		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}