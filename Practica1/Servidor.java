// SERVIDOR
import java.net.*;
import java.io.*;

public class Servidor {
	private static String rutaServer = "./serverP1/";
	private static File[] list;

	//Valor de la bandera = 0
	public static void RecibirArchivos(DataInputStream dis) throws IOException {
		String nombre = dis.readUTF();
		long tam = dis.readLong();

		System.out.println("\nSe recibe el archivo " + nombre + " con " + tam + " bytes");

		nombre = rutaServer + nombre;

		DataOutputStream dos = new DataOutputStream(new FileOutputStream(nombre)); // OutputStream
		
		long recibidos = 0;
		int n = 0, porciento = 0;
		byte[] b = new byte[2000];

		while(recibidos < tam) {
			n = dis.read(b);
			dos.write(b, 0, n);
			dos.flush();
			recibidos += n;
			porciento = (int)((recibidos * 100) / tam);
			//System.out.println("\r Recibiendo el " + porciento + "% --- " + recibidos + "/" + tam + " bytes");
		} //while

		System.out.println("Archivo " + nombre + " recibido.");
		dos.close();
	}//Recibir

	//Valor de la bandera = 1
	public static void ActualizarCliente(Socket cl, DataInputStream dis, String path) throws IOException {
		File archivosRuta = new File(path);
		
		if(!archivosRuta.exists()) {
			archivosRuta.mkdir();
		}//if

		list = archivosRuta.listFiles();

		DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); // OutputStream

		dos.writeInt(list.length);
		dos.flush();

		String info = "";
        for (File f : list) {
            if (f.isDirectory()) { 
                //walk( f.getAbsolutePath() ); 
                info = "" + f.getAbsoluteFile();
                System.out.println("Dir: " + f.getAbsoluteFile()); 
            }//if
            else { 
            	info = f.getName() + "  -------  " + f.length() + " bytes";
                System.out.println("File: " + f.getAbsoluteFile()); 
            }//else
            dos.writeUTF(info);
            dos.flush();   
        }//for
        dos.close();
        System.out.println("Informacion enviada al cliente: Request atendido."); 
	}//Actualizar

	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(4321);
			s.setReuseAddress(true);
			System.out.println("Servidor de archivos iniciado, esperando cliente...");

			// Espera clientes
			for( ; ; ) {
				Socket cl = s.accept();
				System.out.println("\n\nCliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
			
				DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

				int bandera = dis.readInt();

				if(bandera == 0) {
					//Subir archivos -> El servidor recibe
					RecibirArchivos(dis);
				}
				else if (bandera == 1) {
					//Ver archivos / Actualizar -> El servidor envia los nombres de los archivos
					ActualizarCliente(cl, dis, rutaServer);
				}
				else if (bandera == 2) {
					//Descargar archivos -> El servidor prepara y envia archivos

				}
				else if (bandera == 3) {
					//Abrir carpeta -> El servidor envia los nombres de los contenidos de la carpeta seleccionada
					int ubicacionRuta = dis.readInt();
					String nuevaRuta = "" + list[ubicacionRuta].getAbsoluteFile();
					ActualizarCliente(cl, dis, nuevaRuta);
				}
				else {
					System.out.println("Error al atender la solicitud del cliente.");
				}

				dis.close();
				cl.close();
			}//for
		}catch(Exception e) {
			e.printStackTrace();
		}//catch
	}//main
}