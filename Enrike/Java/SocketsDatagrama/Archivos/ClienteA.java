import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;
import java.util.*;

public class ClienteA{
	public static void main(String[] args){
		try{
			JFileChooser jf = new JFileChooser();
			//jf.setMultiSelectionEnbled(true);
			int r = jf.showOpenDialog(null);

			if(r == JFileChooser.APPROVE_OPTION)
			{
				String host = "127.0.0.1";
				int pto = 1234;
				InetAddress dst = null;
				int limite = 1040;

				try{
					dst = InetAddress.getByName(host);
				}catch(UnknownHostException e){
					System.err.println("La direccion no es valida.");
					System.exit(1);
				}//catch

				File f = jf.getSelectedFile();
				String nombre = f.getName();
				int tam = (int) f.length();
				String path = f.getAbsolutePath();
				int numPartes = tam / limite;
				if(tam % limite > 0) 
					numPartes++;

				ArrayList<byte[]> bytesArchivo = new ArrayList<byte[]>();
				int parte = 0;
				int n = 0;

				DataInputStream dis = new DataInputStream(new FileInputStream(path));
				
				while(parte < numPartes){
					byte[] b = new byte[limite];
					n = dis.read(b);
					bytesArchivo.add(parte, b);
					parte++;
				}//while
				dis.close();

				System.out.println("\nSe envia el archivo " + path + " con " + tam + " bytes de " + numPartes + " partes.");
				
				DatagramSocket cl = new DatagramSocket();
				
				String hs = nombre + "-" + tam + "-" + numPartes;
				byte[] bhs = hs.getBytes();
				DatagramPacket handshake = new DatagramPacket(bhs, bhs.length, dst, pto);
				cl.send(handshake);
				System.out.println("\nHandShake enviado al servidor.");

				DatagramPacket respuesta = new DatagramPacket(new byte[65535], 65535);
				for( ; ; ){	
					cl.receive(respuesta);
					String res = new String(respuesta.getData(), 0 , respuesta.getLength());
					
					if(res.equalsIgnoreCase("finalizado")){
						System.out.println("Archivo enviado.");
						break;
					}//if
					else{
						int parteReq = Integer.parseInt(res);
						byte[] enviar = bytesArchivo.get(parteReq);
						System.out.println("Enviando parte: " + parteReq);
						
						DatagramPacket enviarParte = new DatagramPacket(enviar, enviar.length, dst, pto);
						cl.send(enviarParte);
						System.out.println("Bytes enviados.");
					}//else
				}//for

				cl.close();
			}//if
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
