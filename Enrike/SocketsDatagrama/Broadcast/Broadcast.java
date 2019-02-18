import java.net.*;
import java.io.*;

public class Broadcast{
	public static void main(String[] args){
		try{
			DatagramSocket s = new DatagramSocket(1234);
			s.setReuseAddress(true);
			String dir = "255.255.255.255";
			InetAddress dst = null;
			s.setBroadcast(true);
			try{
				dst = InetAddress.getByName(dir);
			}catch(UnknownHostException e){
				System.err.println("Direccion no válida");
				System.exit(1);
			}//catch

			String msj = "Un mensaje por Broadcast";
			byte[] b = msj.getBytes();
			DatagramPacket p = new DatagramPacket(b, b.length, dst, 5678);
			s.send(p);
			s.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}