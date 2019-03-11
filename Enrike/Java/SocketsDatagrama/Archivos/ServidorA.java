import java.net.*;
import java.io.*;
import java.util.*;

public class ServidorA{
	public static void main(String[] args){
		try{
			DatagramSocket s = new DatagramSocket(1234);
			System.out.println("Servidor de datagrams iniciado, esperando datagramas...");

			for( ; ; ){
				DatagramPacket p = new DatagramPacket(new byte[65535], 65535);
				s.receive(p);
				InetAddress dir = p.getAddress();
				int pto = p.getPort();
				System.out.println("Datagrama recibido desde: " + dir + ": " + pto);
				
				String hs = new String(p.getData(), 0, p.getLength());

				String[] handshake = hs.split("-");
				String nombre = handshake[0];
				int tam = Integer.parseInt(handshake[1]);
				int numPartes = Integer.parseInt(handshake[2]);
				System.out.println("HandShake recibido. Archivo: " + nombre + " de tam " + tam + " de " + numPartes + " partes.");
				int cont = 0;

				ArrayList<byte[]> bytesArchivo = new ArrayList<byte[]>();


				while(cont < numPartes){
					String parte = Integer.toString(cont);
					byte[] bParte = parte.getBytes();
					DatagramPacket parteReq = new DatagramPacket(bParte, bParte.length, dir, pto);
					s.send(parteReq);
					System.out.println("Solicitando parte: " + parte);

					DatagramPacket parteArchivo = new DatagramPacket(new byte[65535], 65535);
					s.receive(parteArchivo);
					bytesArchivo.add(cont, parteArchivo.getData());
					System.out.println("Bytes recibidos.");
					cont++;
				}

				String fin = "finalizado";
				byte[] bfin = fin.getBytes();
				DatagramPacket pfin = new DatagramPacket(bfin, bfin.length, dir, pto);
				s.send(pfin);
				System.out.println("Total de partes recibidas, construyendo archivo...");

				DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
				
				int n = 0, i = 0;
				byte[] b = new byte[1040];

				while(i < numPartes){
					DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytesArchivo.get(i)));
                    while((n = dis.read(b)) != -1){
                        dos.write(b, 0, n);
                        dos.flush();
                    }
                    dis.close();
                    i++;
				}
				dos.close();

				System.out.println("Archivo " + nombre + " escrito.");
			}//for
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
