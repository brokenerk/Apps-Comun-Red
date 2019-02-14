import java.net.*;
import java.io.*;

public class CECO{
	public static void main(String[] args){
		try{
			int limite = 2000;
			DatagramSocket cl = new DatagramSocket();
			String msj = "Un mensaje sobre datagrams..";
			byte[] b = msj.getBytes();
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

			if(b.length > limite) {
				ByteArrayInputStream bais2 = new ByteArrayInputStream(b);
				byte[] b2 = new byte[100];
				int n = 0;
				while((n = bais2.read(b2))! = -1) {
					DatagramPacket p1 = new DatagramPacket(b2, n, dts, pto);
					cl.send(p1);
					DatagramPacket p2 = new DatagramPacket(new byte[100], 100);
					cl.receive(p2);
					System.out.println("Datagrama de eco recibido desde: " + p2.getAddress() + ": " + p2.getPort() + " con los datos:");
					String datos = new String(p2.getData(), 0, p2.getLength());
					System.out.println("Eco: " + datos);
				}
			}
			DatagramPacket p = new DatagramPacket(b, b.length, dst, pto);
			cl.send(p);
			cl.close();

		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
