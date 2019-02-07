// SERVIDOR
import java.net.*;
import java.io.*;

public class Recibe
{
	public static void main(String[] args)
	{
		try
		{
			ServerSocket s = new ServerSocket(1234);
			s.setReuseAddress(true);
			System.out.println("Servidor de archivos iniciado, esperando cliente...");

			for( ; ; )
			{
				Socket cl = s.accept();
				System.out.println("Cliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
			
				DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

				String nombre = dis.readUTF();
				long tam = dis.readLong();

				System.out.println("\nSe recibe el archivo " + nombre + " con " + tam + " bytes");

				DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre)); // OutputStream
				
				long recibidos = 0;
				int n = 0, porciento = 0;
				byte[] b = new byte[2000];

				while(recibidos < tam)
				{
					n = dis.read(b);
					dos.write(b, 0, n);
					dos.flush();
					recibidos += n;
					porciento = (int)((recibidos * 100) / tam);
					System.out.println("\r Recibiendo el " + porciento + "% --- " + recibidos + "/" + tam + " bytes");
				}//while

				dis.close();
				dos.close();
				cl.close();
				System.out.println("Archivo " + nombre + " recibido.");

			}//for
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}