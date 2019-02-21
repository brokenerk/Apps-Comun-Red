import java.net.*;
import java.io.*;

public class SHMM{
	public static void main(String[] args){
		try{
			MulticastSocket s = new MulticastSocket(7777);
			s.setReuseAddress(true);
			s.setTimeToLive(255);
			//InetAddress gpo = InetAddress.getByName("229.1.2.3");
			InetAddress gpo = InetAddress.getByName("ff3e:40:2001::1");
			String msj = "Un mensaje enviado por multicast";
			byte[] b = msj.getBytes();
			System.out.println("Servicio iniciado... comienza envio de anuncios");

			for( ; ; ){
				DatagramPacket p = new DatagramPacket(b, b.length, gpo, 7778);
				s.send(p);
				System.out.println("Mensaje enviado con un ttl = " + s.getTimeToLive());
				try{
					Thread.sleep(5000);

				}catch(InterruptedException ie){}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}