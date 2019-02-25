import java.net.*;
import java.io.*;

public class Envia{
	public static void main(String[] args){
		try{
			DatagramSocket cl = new DatagramSocket(1234);
			String msj = "Un mensaje sobre datagrams..";
			byte[] b = msj.getBytes();
			String host = "127.0.0.1";
			int pto = 1234;
			InetAddress dst = null;

			try{
				dst = InetAddress.getByName(host);
			}catch(UnknownHostException e){
				System.err.println("La direccion no es valida.");
				System.exit(1);
			}//catch

			DatagramPacket p = new DatagramPacket(b, b.length, dst, pto);
			cl.send(p);
			cl.close();
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class

/*
	ByteArrayOutputStream baos = new ByteArrayOutputSteam();
	DataOutputStream dos = new DataOutputStream(baos);
	dos.writeInt(5);
	dos.writeFloat(6.0f);
	dos.writeLong(7);
	dos.flush();
	byte[] b1 = baos.toByteArray();
	DatagramPacket p1 = new DatagramPacket(b1, b1.length, dst, pto);
	cl.send(p1);
*/