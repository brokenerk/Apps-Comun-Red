import java.net.*;
import java.io.*;

public class CHMM{
	public static void main(String[] args){
		try{
			MulticastSocket cl = new MulticastSocket(7778);
			cl.setReuseAddress(true);
			//InetAddress gpo = InetAddress.getByName("229.1.2.3");
			InetAddress gpo = InetAddress.getByName("ff3e:40:2001::1");
			cl.joinGroup(gpo);
			System.out.println("Servicio iniciado y unido al grupo... comienza escuchas de anuncios");

			for( ; ; ){
				DatagramPacket p = new DatagramPacket(new byte[65535], 65535);
				cl.receive(p);
				System.out.println("Datagrama recibido desde: " + p.getAddress() + ": " + p.getPort() + " con los datos:" + new String(p.getData(), 0, p.getLength()));
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}