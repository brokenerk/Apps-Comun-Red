import java.net.*;
import java.io.*;

public class CECO_O{
	public static void main(String[] args){
		try{
			int limite = 10;
			DatagramSocket cl = new DatagramSocket();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String host = "127.0.0.1";
			int pto = 1234;
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
					byte[] b2 = new byte[limite];
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					ByteArrayInputStream bais = new ByteArrayInputStream(b1);

					int n = 0, c = 0;
					int np = (int)(b1.length / b2.length);

					if(b1.length % b2.length > 0){
						np += 1;
						while(c < np){
							n = bais.read(b2);
							Datos d = new Datos(c + 1, b2, np);
							oos.writeObject(d);
							oos.flush();
							byte[] tmp = baos.toByteArray();
							DatagramPacket dp = new DatagramPacket(tmp, tmp.length, dst, pto);
							cl.send(dp);
							c++;
						}//while
					}//if
				}//if
			}
			br.close();
			cl.close();

		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
