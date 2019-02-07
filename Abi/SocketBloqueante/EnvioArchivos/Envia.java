// Envio de archivos

import javax.swing.JFileChooser;
import java.net.*;
import java.io.*;

public class Envia {
	public static void main(String[] args) {
		try {
			JFileChooser jf = new JFileChooser();
			int r = jf.showOpenDialog(null);
			if(r == JFileChooser.APPROVE_OPTION);
			File f = jf.getSelectedFile();
			String nombre = f.getName();
			long tam = f.length();
			String path = f.getAbsolutePath();
			int pto = 8888;
			String host = "127.0.0.1";
			Socket cl = new Socket(host, pto);
			System.out.println("Se enviara el archivo " + path + "con" + tam + "bytes\n");

			DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
			DataInputStream dis = new DataInputStream(new FileInputStream(path));

			dos.writeUTF(nombre);
			dos.flush();
			dos.writeLong(tam);

			long enviados = 0;
			int n = 0, porciento = 0;
			byte[]b = new byte[2000];
			while(enviados < tam) {
				n = dis.read(b);
				// De donde saca los datos, cuantos datos se va a saltar, lo que se pudo leer
				dos.write(b, 0, n);
				dos.flush();
				enviados += n;
				porciento = (int)((enviados * 100) / tam);
				System.out.print("\r Enviando el " + porciento + "%"); 
			} // while

			dis.close();
			dos.close();
			cl.close();
		} // fin
		catch(Exception e) {
			e.printStackTrace();
		} // catch
	} // main
} // class