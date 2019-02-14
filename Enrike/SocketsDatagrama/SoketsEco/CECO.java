import java.net.*;
import java.io.*;

public class CECO{
	public static void main(String[] args){
		try{
			int limite = 5;
			DatagramSocket cl = new DatagramSocket();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Escribe un mensaje:");
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
				String msj = br.readLine();

				if(msj.compareToIgnoreCase("salir") == 0)
				{
					break;
				}

				byte[] b = msj.getBytes();

				if(b.length > limite){

					ByteArrayInputStream bais = new ByteArrayInputStream(b);
					byte[] b2 = new byte[2];
					int n = 0;

					while((n = bais.read(b2)) != -1) {
						DatagramPacket p1 = new DatagramPacket(b2, n, dst, pto);
						cl.send(p1);
						DatagramPacket p2 = new DatagramPacket(new byte[10], 10);
						cl.receive(p2);
						String eco = new String(p2.getData(), 0, p2.getLength());
						System.out.println("Eco 1: " + eco);
					}
				}
				else{
					DatagramPacket p1 = new DatagramPacket(b, b.length, dst, pto);
					cl.send(p1);
					DatagramPacket p2 = new DatagramPacket(new byte[10], 10);
					cl.receive(p2);
					String eco = new String(p2.getData(), 0, p2.getLength());
					System.out.println("Eco 2: " + eco);
				}
			}
			br.close();
			cl.close();

		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
