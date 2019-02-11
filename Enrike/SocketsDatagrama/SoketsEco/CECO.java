import java.net.*;
import java.io.*;

public class CECO{
	public static void main(String[] args){
		try{
			int limite = 65535;
			DatagramSocket cl = new DatagramSocket();
			String msj = "Un mensaje sobre datagrams..";
			byte[] b = msj.getBytes();
			String host = "127.0.0.1";
			int pto = 1234;
			InetAddress dst = null;

			if(b.length > limite) {

				try {
					dst = InetAddress.getByName(host);
					ByteArrayInputStream bai2 = new ByteArrayInputStream(b);
					byte[] b2 = new byte[65535];
					int n = 0;
					while((n = bai2.read(b2))! = -1) {
						DatagramPacket p1 = new DatagramPacket(b2, n, dts, pto);
						cl.send();
					}
				}
				catch(UnknownHostException e){
					System.err.println("La direccion no es valida.");
					System.exit(1);
				}//catch
			}



			DatagramPacket p = new DatagramPacket(b, b.length, dst, pto);
			cl.send(p);
			cl.close();
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
