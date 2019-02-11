import java.net.*;
import java.io*;

public class Envia {
	public static void main(String[] args) {
		try {
			DatagramSocket cl = new DatagramSocket();
			String msj = "Un mensaje sobre datagramas...";
			byte[]  b = msj.getBytes();
			String host = "127.0.0.1";
			int pto = 1234;
			InetAddress dst = null;
			try {
				dst = InetAddress.getByName(host);
			}
			catch(UnknowHostException u) {
				System.err.println("La direccion no es valida..");
				System.exit(1);
			} // catch
			DatagramPacket p = new DatagramPacket(b, b.length, dst, pto);
			cl.send(p);
			cl.close(); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}