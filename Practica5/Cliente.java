import java.net.*;
import java.io.*;
import java.util.*;

public class Cliente
{
	public static void main(String[] args)
	{
		try
		{
			Controlador ctrl = new Controlador();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Nombre del servidor: "); //servidor1, servidor2
			String servidorActual = br.readLine();

			String hostActual = ctrl.obtenerHostServidor(servidorActual);
			int ptoActual = ctrl.obtenerPtoServidor(servidorActual);

			Socket cl = new Socket(hostActual, ptoActual);
			System.out.println("\nConexion establecida al " + servidorActual);
			System.out.println("Host: " + hostActual + ". Puerto: " + ptoActual);

			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
			DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

			System.out.println("\nPalabra: ");
			String palabra = br.readLine();
			br.close();

			dos.writeUTF(palabra);
			dos.flush();

			String respuesta = dis.readUTF();

			dos.close();
			dis.close();
			cl.close();

			String definicion = respuesta;

			if(respuesta.equals("")){
				String servidorRemoto = ctrl.obtenerServidor(palabra);
				String hostRemoto = ctrl.obtenerHostServidor(servidorRemoto);
				int ptoRemoto = ctrl.obtenerPtoServidor(servidorRemoto);

				Socket cl2 = new Socket(hostRemoto, ptoRemoto);
				System.out.println("\nDefinicion no encontrada. Conectando al " + servidorRemoto);
				System.out.println("Host: " + hostRemoto + ". Puerto: " + ptoRemoto);

				DataOutputStream dos2 = new DataOutputStream(cl2.getOutputStream()); //OutputStream
				DataInputStream dis2 = new DataInputStream(cl2.getInputStream()); // InputStream

				dos2.writeUTF(palabra);
				dos2.flush();

				definicion = dis2.readUTF();

				dos2.close();
				dis2.close();
				cl2.close();
			}

			System.out.println("\n\n" + palabra + ": " + definicion);

			

			

		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}