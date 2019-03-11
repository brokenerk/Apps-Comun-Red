import java.net.*;
import java.io.*;

public class CECO_O{
	public static void main(String[] args){
		try{
			int limite = 5;
			DatagramSocket cl = new DatagramSocket();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String host = "127.0.0.1";
			int pto = 3421;
			InetAddress dst = null;

			try {
				dst = InetAddress.getByName(host);
			}
			catch(UnknownHostException e){
				System.err.println("La direccion no es valida.");
				System.exit(1);
			}//catch

			for( ; ; ) {
				System.out.println("Escribe un mensaje:");
				String msj = br.readLine();
				if(msj.compareToIgnoreCase("salir") == 0)
					break;

				byte[] b1 = msj.getBytes();

				if(b1.length > limite){
					
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					ByteArrayInputStream bais = new ByteArrayInputStream(b1);

					int n = 0, c = 0;
					int np = (int)(b1.length / limite);

					if(b1.length % limite > 0) 
						np++;

					while(c < np){
						byte[] b2 = new byte[limite];
						n = bais.read(b2);
						Datos d = new Datos(c + 1, b2, n, np);
						oos.writeObject(d);
						oos.flush();
						byte[] tmp = baos.toByteArray();
						DatagramPacket dp = new DatagramPacket(tmp, tmp.length, dst, pto);
						cl.send(dp);
						c++;
						System.out.println("Enviada parte: " + d.getParte() + "/" + d.getTotal() + " de " + tmp.length + " bytes.");
						System.out.println("Datos: " + new String(d.getContenido(), 0, d.getContenido().length));
					}//while
					
					oos.close();
					baos.close();
					bais.close();
				}//if
			}
			br.close();
			cl.close();

		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
