// CLIENTE
import javax.swing.JFileChooser;
import java.io.*;
import java.net.*;

public class EnviaM
{
	public static void main(String[] args)
	{
		try
		{
			JFileChooser jf = new JFileChooser();
			jf.setMultiSelectionEnabled(true);
			int r = jf.showOpenDialog(null);

			if(r == JFileChooser.APPROVE_OPTION)
			{
				//File f = jf.getSelectedFile();
				File[] files = jf.getSelectedFiles();

				int pto = 4321;
				String host = "10.0.0.4";
				int numArchivos = files.length;

				System.out.println("\nSe envian " + numArchivos + " archivos");

				for(File f : files)
				{
					String nombre = f.getName();
					long tam = f.length();
					String path = f.getAbsolutePath();

					Socket cl = new Socket(host, pto);
					DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
				
					System.out.println("\nSe envia el archivo " + path + " con " + tam + " bytes");
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
				}//for	
			}//if
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}