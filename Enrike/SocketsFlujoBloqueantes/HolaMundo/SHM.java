import java.net.*;
import java.io.*;

public class SHM
{
	public static void main(String[] args)
	{
		try
		{
			ServerSocket s = new ServerSocket(9999);
			s.setReuseAddress(true);
			//s.setSoLinger(true, s);
			System.out.println("Servidor iniciado, esperando cliente...");

			for( ; ; )
			{
				Socket cl = s.accept();
				System.out.println("Cliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
				String msj = "Un mensaje de saludo";
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
				pw.println(msj);
				pw.flush();
				pw.close();
				cl.close();
			}//for
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}