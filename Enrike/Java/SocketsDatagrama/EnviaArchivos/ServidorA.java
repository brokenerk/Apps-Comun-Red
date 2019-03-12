import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.Thread;

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
				System.out.println("\n\n-----------------------------------------------------------------------------");
				System.out.println("Datagrama recibido desde: " + dir + ": " + pto);
				
				String hs = new String(p.getData(), 0, p.getLength());

				String[] handshake = hs.split("/");
				String nombre = handshake[0];
				int tam = Integer.parseInt(handshake[1]);
				int numPartes = Integer.parseInt(handshake[2]);
				System.out.println("HandShake recibido. Archivo: " + nombre + " de tam " + tam + " de " + numPartes + " partes.\n");
				int cont = 1;

				ArrayList<Boolean> banderas = new ArrayList<Boolean>(numPartes);
				Collections.fill(banderas, false);
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre));
				int bytesRecibidos = 0;

				while(cont <= numPartes || banderas.contains(false)){
					String parte = Integer.toString(cont);
					byte[] bParte = parte.getBytes();
					DatagramPacket parteReq = new DatagramPacket(bParte, bParte.length, dir, pto);
					s.send(parteReq);
					System.out.println("Solicitando parte: " + parte);

					DatagramPacket parteArchivo = new DatagramPacket(new byte[65535], 65535);
					s.receive(parteArchivo);
					System.out.println("Datagrama recibido desde: " + parteArchivo.getAddress() + ": " + parteArchivo.getPort());

					if(parteArchivo.getData() != null){
						byte[] b = new byte[2000];
						int n = 0;
						banderas.add(cont - 1, true);
						DataInputStream dis = new DataInputStream(new ByteArrayInputStream(parteArchivo.getData()));
						n = dis.read(b);
						
						if(tam < 2000)
							n = tam;

						dos.write(b, 0, n);
						dos.flush();
						dis.close();
						Thread.sleep(50);
						cont++;
						tam = tam - n;
						bytesRecibidos = bytesRecibidos + n;
						System.out.println("Parte " + parte + " recibida de " + n + ". Escribiendo bytes en archivo temporal...\n");
					}
				}
				dos.close();

				String fin = "finalizado";
				byte[] bfin = fin.getBytes();
				DatagramPacket pfin = new DatagramPacket(bfin, bfin.length, dir, pto);
				s.send(pfin);
				System.out.println("\nTotal de partes recibidas, construyendo archivo... Archivo " + nombre + " escrito de " + bytesRecibidos + " bytes.");
			}//for
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
