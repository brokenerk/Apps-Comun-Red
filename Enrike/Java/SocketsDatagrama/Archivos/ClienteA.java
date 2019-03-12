import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.Thread;

public class ClienteA{
	public static void main(String[] args){
		try{
			JFileChooser jf = new JFileChooser();
			jf.setMultiSelectionEnabled(true);
			int r = jf.showOpenDialog(null);

			if(r == JFileChooser.APPROVE_OPTION)
			{
				File[] files = jf.getSelectedFiles();
				String host = "127.0.0.1";
				int pto = 1234;
				InetAddress dst = null;
				int limite = 2000;

				try{
					dst = InetAddress.getByName(host);
				}catch(UnknownHostException e){
					System.err.println("La direccion no es valida.");
					System.exit(1);
				}//catch

				for(File f : files)
				{
					String nombre = f.getName();
					int tam = (int) f.length();
					String path = f.getAbsolutePath();
					int numPartes = tam / limite;
					if(tam % limite > 0) 
						numPartes++;

					int parte = 1;
					int n = 0;
					System.out.println("\n\n-----------------------------------------------------------------------------");
					System.out.println("Se envia el archivo " + path + " con " + tam + " bytes de " + numPartes + " partes.");
					DatagramSocket cl = new DatagramSocket();
					
					/*Handshake*/
					String hs = nombre + "/" + tam + "/" + numPartes;
					byte[] bhs = hs.getBytes();
					DatagramPacket handshake = new DatagramPacket(bhs, bhs.length, dst, pto);
					cl.send(handshake);
					System.out.println("HandShake enviado al servidor con datos del archivo.\n");

					DataInputStream dis = new DataInputStream(new FileInputStream(path));
					DatagramPacket respuesta = new DatagramPacket(new byte[65535], 65535);
					int bytesEnviados = 0;

					for( ; ; ){	
						cl.receive(respuesta);
						System.out.println("\nDatagrama recibido desde: " + respuesta.getAddress() + ": " + respuesta.getPort());
						String res = new String(respuesta.getData(), 0 , respuesta.getLength());
						
						if(res.equalsIgnoreCase("finalizado")){
							System.out.println("El servidor indica que se recibieron todas las partes. Archivo " + nombre + " enviado de " + bytesEnviados + " bytes.");
							break;
						}//if
						else{
							int parteReq = Integer.parseInt(res);
							
							if(parte == parteReq){
								byte[] b = new byte[limite];
								n = dis.read(b);
								System.out.print("Enviando parte: " + parteReq);
								DatagramPacket enviarParte = new DatagramPacket(b, n, dst, pto);
								cl.send(enviarParte);
								System.out.println(". " + n + " bytes enviados.");
								parte++;
								bytesEnviados = bytesEnviados + n;
								Thread.sleep(50);
							}
						}//else
					}//for

					dis.close();
					cl.close();
				}//for
			}//if
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class