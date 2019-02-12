import javax.swing.JFileChooser;
import java.io.*;
import java.net.*;

/*Funciones del cliente que haran las peticiones que se requieran al servidor*/
public class Cliente {
	private static int pto = 4321;
	private static String host = "127.0.0.1";
	private static String rutaDirectorios = "";
	public static String sep = System.getProperty("file.separator");
	public static int[] tipoFile;

/*********************************************************************************************
									ABRIR CARPETA
*********************************************************************************************/

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
			tipoFile = new int[numArchivos];

			for(int i = 0; i < numArchivos; i++) {
				String archivoRecibido = dis.readUTF();
				DropBox.modelo.addElement(archivoRecibido);
				tipoFile[i] = dis.readInt();
				//System.out.println("" + archivoRecibido);
			}//for

			dis.close();
			dos.close();
			cl.close();
			//System.out.println("Nueva carpeta abierta: Request recibido.");

    	}catch(Exception e) {
    		e.printStackTrace();
    	}//catch
	}

/*********************************************************************************************
									ENVIAR ARCHIVO
*********************************************************************************************/

	/*
		Descripción: La función permite enviar un archivo o directorio.
		Parametros: Archivo a enviar, Ruta de dónde se encuentra ese archivo
		Regresa: Nada, solo envía el archivo.
	*/
	public static void EnviarArchivo(File f, String pathOrigen, String pathDestino) {
		try {
			if(f.isFile()) {
				Socket cl = new Socket(host, pto);
		        DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream

	    		String nombre = f.getName();
	            long tam = f.length();

	            System.out.println("\nSe envia el archivo " + pathOrigen + " con " + tam + " bytes");
	            DataInputStream dis = new DataInputStream(new FileInputStream(pathOrigen)); // InputStream

	            //La bandera tiene el valor de 0 = Subir archivo
	            dos.writeInt(0); dos.flush();

				//Se envia info de los archivos
	            dos.writeUTF(nombre); dos.flush();
	            dos.writeLong(tam);	dos.flush();
	            dos.writeUTF(pathDestino); dos.flush();

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
	            
	            dis.close(); dos.close(); cl.close();
			} // If
			else {
				Socket cl = new Socket(host, pto);
				DataOutputStream dos = new DataOutputStream(cl.getOutputStream());
				String nombre = f.getName();
				String ruta = f.getAbsolutePath();
				System.out.println("Mi nombre: " + nombre + " Mi ruta: " + ruta);
				String aux = rutaDirectorios;
				rutaDirectorios = rutaDirectorios + sep + nombre;
				//La bandera tiene el valor de 4 = Subir Carpeta
	            dos.writeInt(4);
	            dos.flush();
				//Se envia info de los archivos
	            dos.writeUTF(rutaDirectorios);
	            dos.flush();
	            // Envio los archivos que pertenecen al directorio creado
			    File folder = new File(ruta);
			    File[] files = folder.listFiles();
			    for(File file : files)	{
	            	String path = rutaDirectorios + sep + file.getName();
	            	System.out.println("Ruta destin en el servidor:" + path);
	            	EnviarArchivo(file, file.getAbsolutePath(), path);
	        	}// for
	        	rutaDirectorios = aux;
	            dos.close();
				cl.close();
			} // Else		
		} // try
		catch(Exception e) {
			e.printStackTrace();
		}
	} // Enviar archivo

/*********************************************************************************************
									SELECCIONAR ARCHIVOS
*********************************************************************************************/

	// Ennvia muchos archivos al servidor
	public static void SeleccionarArchivos() {
		try {
	        JFileChooser jf = new JFileChooser();
	        jf.setMultiSelectionEnabled(true);
	        jf.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	        int r = jf.showOpenDialog(null);

	        if(r == JFileChooser.APPROVE_OPTION) {	
	        	rutaDirectorios = "";        	
	            File[] files = jf.getSelectedFiles();
	            for(File file : files)	{
	            	String rutaOrigen = file.getAbsolutePath();
	            	// Tipo caso base: La primera vez que mandemos un archivo
	            	// Siempre estará en la raíz del servidor
	            	EnviarArchivo(file, rutaOrigen, file.getName());
	        	}//for
	        }//if   
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

/*********************************************************************************************
									ACTUALIZAR
*********************************************************************************************/

    public static void Actualizar(){
    	try {
    		Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
			
			//La bandera tiene el valor de 1 = Actualizar 
			dos.writeInt(1);
			dos.flush();

			DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

			int numArchivos = dis.readInt();
			tipoFile = new int[numArchivos];

			for(int i = 0; i < numArchivos; i++) {
				String archivoRecibido = dis.readUTF();
				DropBox.modelo.addElement(archivoRecibido);
				tipoFile[i] = dis.readInt();
				//System.out.println("" + archivoRecibido);
			}//for

			dis.close();
			dos.close();
			cl.close();
			//System.out.println("Carpeta del cliente actualizada: Request recibido.");

    	}catch(Exception e) {
    		e.printStackTrace();
    	}//catch
    }//Actualizar

/*********************************************************************************************
								RECIBIR ARCHIVOS
*********************************************************************************************/
	public static void RecibirArchivos(String[] nombresArchivos, int tama) {
		try {
			System.out.println("Estoy del lado del cliente\n");
			String nombre = "." + sep + "Escritorio";
    		Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
			DataOutputStream dosArchivo = new DataOutputStream(new FileOutputStream(nombre)); // OutputStream

			DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

			//La bandera tiene el valor de 2 = Descargar seleccion
			dos.writeInt(2); dos.flush();

			dos.writeInt(tama); dos.flush();
			
			//Enviamos los indices de los archivos seleccionados
			String aux = "";
			int i;
			for(i = 0; i < tama; i++) {
				aux = nombresArchivos[i];
				dos.writeUTF(aux);
				dos.flush();
			}
<<<<<<< HEAD

=======
			// AQUI ME QUEDE
			//if(index.length > 0)
				//String path = index[i];
			// Enviamos la path de los archivos
			//dos.writeUTF();
			/* Recibimos el archivo ZIP
			DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream
>>>>>>> f09a416b63b5b503ac7b88473c5d38fa16a3a05e
			long tam = dis.readLong();
			System.out.println("\nSe recibe el archivo " + nombre + " con " + tam + "bytes");
			
			long recibidos = 0;
			int n = 0, porciento = 0;
			byte[] b = new byte[2000];

			while(recibidos < tam) {
				n = dis.read(b);
				dosArchivo.write(b, 0, n);
				dosArchivo.flush();
				recibidos += n;
				porciento = (int)((recibidos * 100) / tam);
				//System.out.println("\r Recibiendo el " + porciento + "% --- " + recibidos + "/" + tam + " bytes");
			} // while

			System.out.println("Archivo " + nombre + " recibido.");
			dos.close();
			dis.close();
			dosArchivo.close();
			cl.close();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}//catch
	}

}


