// CLIENTE
import javax.swing.JFileChooser;
import java.io.*;
import java.net.*;

public class Envia
{
	public static void main(String[] args)
	{
		try
		{
			JFileChooser jf = new JFileChooser();
			//jf.setMultiSelectionEnbled(true);
			int r = jf.showOpenDialog(null);

			if(r == JFileChooser.APPROVE_OPTION)
			{
				File f = jf.getSelectedFile();
				//File[] f = jf.getSelectedFiles();
				String nombre = f.getName();
				long tam = f.length();
				String path = f.getAbsolutePath();
				
				int pto = 1234;
				String host = "127.0.0.1";
				Socket cl = new Socket(host, pto);
				System.out.println("\nSe envia el archivo " + path + " con " + tam + " bytes");

				DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
				DataInputStream dis = new DataInputStream(new FileInputStream(path)); // InputStream

				dos.writeUTF(nombre);
				dos.flush();
				dos.writeLong(tam);
				dos.flush();

				long enviados = 0;
				int n = 0, porciento = 0;
				byte[] b = new byte[2000];

				while(enviados < tam)
				{
					n = dis.read(b);
					dos.write(b, 0, n);
					dos.flush();
					enviados += n;
					porciento = (int)((enviados * 100) / tam);
					System.out.println("\r Enviando el " + porciento + "% --- " + enviados + "/" + tam + " bytes");
				}//while

				dis.close();
				dos.close();
				cl.close();

				System.out.println("Archivo " + nombre + " enviado.");

			}//if
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}