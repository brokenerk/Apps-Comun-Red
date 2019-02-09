import javax.swing.JFileChooser;
import java.io.*;
import java.net.*;

/*Funciones del cliente que haran las peticiones que se requieran al servidor*/

public class Cliente {
	private static int pto = 4321;
	private static String host = "127.0.0.1";

	// Funcion abrir carpetas del servidor en el cliente
	public static void AbrirCarpeta(int indice){
		try {
    		Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
			
			//La bandera tiene el valor de 3 = AbrirCarpeta
			dos.writeInt(3);
			dos.flush();

			//Enviamos el indice en donde se encuentra la carpeta dentro del arreglo de Files[]
			dos.writeInt(indice);
			dos.flush();

			DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

			int numArchivos = dis.readInt();

			for(int i = 0; i < numArchivos; i++) {
				String archivoRecibido = dis.readUTF();
				DropBox.modelo.addElement(archivoRecibido);
				//System.out.println("" + archivoRecibido);
			}//for

			dis.close();
			dos.close();
			cl.close();
			System.out.println("Nueva carpeta abierta: Request recibido.");

    	}catch(Exception e) {
    		e.printStackTrace();
    	}//catch
	}

	// Funcion envia muchos archivos al servidor
	public static void EnviarArchivos() {
		try {
	        // Socket cliente para enviar muchos archivos a la vez
	        JFileChooser jf = new JFileChooser();
	        jf.setMultiSelectionEnabled(true);
	        // Permite seleccionar carpetas y archivos
	        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	        int r = jf.showOpenDialog(null);

	        if(r == JFileChooser.APPROVE_OPTION) {
	        	
	        	Socket cl = new Socket(host, pto);
		        DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream

	            File[] files = jf.getSelectedFiles();

	            for(File f : files)	{

	                String nombre = f.getName();
		            long tam = f.length();
		            String path = f.getAbsolutePath();

		            System.out.println("\nSe envia el archivo " + path + " con " + tam + " bytes");
		            DataInputStream dis = new DataInputStream(new FileInputStream(path)); // InputStream

		            //La bandera tiene el valor de 0 = Subir archivo
		            dos.writeInt(0);
		            dos.flush();

					//Se envia info de los archivos
		            dos.writeUTF(nombre);
		            dos.flush();
		            dos.writeLong(tam);
		            dos.flush();

		            long enviados = 0;
		            int n = 0, porciento = 0;
		            byte[] b = new byte[2000];

		            while(enviados < tam) {
		                n = dis.read(b);
		                dos.write(b, 0, n);
		                dos.flush();
		                enviados += n;
		                porciento = (int)((enviados * 100) / tam);
		                //System.out.println("\r Enviando el " + porciento + "% --- " + enviados + "/" + tam + " bytes");
		            } //while
		            
		            dis.close();
		            dos.close();
		            cl.close();
		            System.out.println("Archivo " + nombre + " enviado.");
	        	}//for	
	        }//if   
        }
        catch(Exception e) {
            e.printStackTrace();
        }//catch
    }//EnviarArchivos

    public static void Actualizar(){
    	try {
    		Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
			
			//La bandera tiene el valor de 1 = Actualizar 
			dos.writeInt(1);
			dos.flush();

			DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

			int numArchivos = dis.readInt();

			for(int i = 0; i < numArchivos; i++) {
				String archivoRecibido = dis.readUTF();
				DropBox.modelo.addElement(archivoRecibido);
				//System.out.println("" + archivoRecibido);
			}//for

			dis.close();
			dos.close();
			cl.close();
			System.out.println("Carpeta del cliente actualizada: Request recibido.");

    	}catch(Exception e) {
    		e.printStackTrace();
    	}//catch
    }//Actualizar
}