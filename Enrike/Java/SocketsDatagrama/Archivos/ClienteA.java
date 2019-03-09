import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class ClienteA{
	public static void main(String[] args){
		try{
			JFileChooser jf = new JFileChooser();
			//jf.setMultiSelectionEnbled(true);
			int r = jf.showOpenDialog(null);

			if(r == JFileChooser.APPROVE_OPTION)
			{
				DatagramSocket cl = new DatagramSocket();
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
			
				System.out.println("\nSe envia el archivo " + path + " con " + tam + " bytes");


				
				
				DataInputStream dis = new DataInputStream(new FileInputStream(path)); // InputStream

				byte[] b1 = new byte[tam];
				int aux = dis.read(b1);

				if(tam > limite){
					byte[] b2 = new byte[limite];
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);
					ByteArrayInputStream bais = new ByteArrayInputStream(b1);

					int n = 0, parte = 1;
					int npartes = (int)(b1.length / b2.length);

					if(b1.length % b2.length > 0){
						npartes += 1;
						while(parte < npartes){
							n = bais.read(b2);
							Archivo d = new Archivo(nombre, parte, b2, npartes);
							oos.writeObject(d);
							oos.flush();
							byte[] tmp = baos.toByteArray();
							DatagramPacket dp = new DatagramPacket(tmp, tmp.length, dst, pto);
							System.out.println("" + tmp.length);
							cl.send(dp);
							System.out.println("Enviada parte: " + parte + "/" + npartes + " de " + n + " bytes.");
							parte++;
						}//while
					}//if
					baos.close();
					oos.close();
					bais.close();
				}//if
				else{
					DatagramPacket dp1 = new DatagramPacket(b1, b1.length, dst, pto);
					cl.send(dp1);
				}
				System.out.println("\nArchivo " + path + " con " + tam + " bytes enviado");
				dis.close();
				cl.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}//class
