import java.net.*;
import java.io.*;

public class SECO
{
	public static void main(String[] args)
	{
		try
		{
			ServerSocket s = new ServerSocket(8888);
			s.setReuseAddress(true);
			//s.setSoLinger(true, s);
			System.out.println("Servidor de eco iniciado, esperando cliente...");

			for( ; ; )
			{
				Socket cl = s.accept();
				System.out.println("Cliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
				
				PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
				BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));

				for( ; ; )
				{
					String msj = br.readLine();
					if(msj.compareToIgnoreCase("salir") == 0)
					{
						System.out.println("Cliente se va");
						br.close();
						pw.close();
						cl.close();
						break;
					}
					else
					{
						String eco = msj + " eco";
						pw.println(eco);
						pw.flush();
						//continue
					}//else
				}//for
			}//for
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}