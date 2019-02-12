// SERVIDOR
import java.net.*;
import java.io.*;
import java.util.zip.*;

public class Servidor {
//	private static String rutaServer = ".\\serverP1\\";
	public static String sep = System.getProperty("file.separator");
	private static String rutaServer = "." + sep + "serverP1" + sep;
	private static File[] list;
	private static String rutaActual = "";

/*********************************************************************************************
									0. RECIBIR ARCHIVOS
*********************************************************************************************/
	// Valor de la bandera = 0
	public static void RecibirArchivos(DataInputStream dis, String nombre) throws IOException {
		long tam = dis.readLong();
		String pathDestino = dis.readUTF();
		nombre = rutaServer + pathDestino;
		
		System.out.println("\nSe recibe el archivo " + nombre + " con " + tam + "bytes");
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
		} // while

		System.out.println("Archivo " + nombre + " recibido.");
		dos.close();
		dis.close();
	} // RecibirArchivos

/*********************************************************************************************
								1. ACTUALIZAR CLIENTES
*********************************************************************************************/
	//Valor de la bandera = 1
	public static void ActualizarCliente(Socket cl, DataInputStream dis, String path, int bandera) throws IOException {
		File archivosRuta = new File(path);
		
		if(!archivosRuta.exists()) {
			archivosRuta.mkdir();
		}//if

		if(bandera == 1) {
			rutaActual = rutaActual + sep + archivosRuta.getName();
			System.out.println("Ubicacion: "+rutaActual);
		}

		list = archivosRuta.listFiles();

		DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); // OutputStream

		dos.writeInt(list.length);
		dos.flush();

		String info = "";
		int tipo = 0;

        for (File f : list) {
            if (f.isDirectory()) { 
                //walk( f.getAbsolutePath() ); 
                tipo = 1;
                if(bandera == 0) {//Ruta raiz - Inicio
                	info = "." + sep + f.getName();
                }
                else {//Abrir ruta y concatenar
                	info = "." + rutaActual + sep + f.getName();
                }
            }//if
            else { 
            	tipo = 2;
            	if(bandera == 0){//Ruta raiz - Inicio
            		info = f.getName();            		
            		//info = f.getName() + "  -------  " + f.length() + " bytes";
            	}
            	else {//Abrir ruta y concatenar
            		info = "." + rutaActual + sep + f.getName();
            		//info = "." + rutaActual + sep + f.getName() + "  -------  " + f.length() + " bytes";
            	}
            }//else
            dos.writeUTF(info);
            dos.flush();   
            dos.writeInt(tipo);
            dos.flush();

            tipo = 0;
        }//for
        dos.close();
        //System.out.println("Informacion enviada al cliente: Request atendido."); 
	}//Actualizar

/*********************************************************************************************
							CREAR ARCHIVO .ZIP
*********************************************************************************************/
	
	public static void crearZIP(DataInputStream dis, int tam) {
		try {
			//Enviamos los indices de los archivos seleccionados
			String[] nombreArchivos = new String[tam];
			String aux = "";
			int i, j;
			for(i = 0; i < tam; i++) {
				nombreArchivos[i] = dis.readUTF();
				System.out.println("\nRecibi el nombre: " + nombreArchivos[i]);
			}
			dis.close();
			// Quito ./ al nombre del directorio
			char aux1 = ' ', aux2 = ' ';
			String nombre = ""; 
			for(i = 0; i < tam; i++) {
				aux1 = nombreArchivos[i].charAt(0);
				if( aux1 == '.') {
					for(j = 2; j < nombreArchivos[i].length(); j++)
						nombre = nombre + Character.toString(nombreArchivos[i].charAt(j));
					nombreArchivos[i] = nombre;
					nombre = "";
				}
			}
		    String destino = rutaServer + "DropBox.zip";
		    FileOutputStream fos = new FileOutputStream(destino);
		    ZipOutputStream zipOut = new ZipOutputStream(fos);
			String sourceFile = "";
			for(i = 0; i < tam; i++) {
				// Le doy la ruta de mi archivo o directorio
				sourceFile = rutaServer + nombreArchivos[i];
				File fileToZip = new File(sourceFile);
		    	zipFile(fileToZip, fileToZip.getName(), zipOut);
		    	sourceFile = " ";
			}
			zipOut.close();
		    fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

/*********************************************************************************************
										ZIP
*********************************************************************************************/

	public static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith(sep)) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + sep));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + sep + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
        System.out.println("TERMINE DE COMPRIMIR LOS ARCHIVOS");
    }

/*********************************************************************************************
									EnviarArchivo
*********************************************************************************************/
    public static void EnviarArchivo(DataInputStream dis, DataOutputStream dos, File f, String pathOrigen) {
		try {
    		String nombre = f.getName();
            long tam = f.length();
            System.out.println("\nSe envia el archivo " + nombre + " con " + tam + " bytes");

			//Se envia info de los archivos
            //dos.writeUTF(nombre); dos.flush();
            dos.writeLong(tam);	dos.flush();

            long enviados = 0;
            int n = 0, porciento = 0;
            byte[] b = new byte[2000];

            while(enviados < tam) {
                n = dis.read(b);
                dos.write(b, 0, n);
                dos.flush();
                enviados += n;
                porciento = (int)((enviados * 100) / tam);
                System.out.println("\r Enviando el " + porciento + "% --- " + enviados + "/" + tam + " bytes");
            } //while
            
            dis.close(); dos.close();
		} // try
		catch(Exception e) {
			e.printStackTrace();
		}
	} // Enviar archivo

/*********************************************************************************************
										MAIN
*********************************************************************************************/

	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(4321);
			s.setReuseAddress(true);
			System.out.println("Servidor de archivos iniciado, esperando cliente...");

			// Espera clientes
			for( ; ; ) {
				Socket cl = s.accept();
				System.out.println("\n\nCliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
				DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream
				DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream

				int bandera = dis.readInt();

				if(bandera == 0) {
					//Subir un archivo -> El servidor recibe
					String nombre = dis.readUTF();
					System.out.println("Recibi ... " + nombre);
					RecibirArchivos(dis, nombre);
				}
				else if (bandera == 1) {
					//Ver archivos / Actualizar -> El servidor envia los nombres de los archivos
					//Bandera = 0. Se actualiza la carpeta raiz
					rutaActual = "";
					ActualizarCliente(cl, dis, rutaServer, 0);

				}
				else if (bandera == 2) {
					//Descargar archivos -> El servidor prepara y envia archivos
					//Subir archivos -> El servidor recibe

					System.out.println("ENTRE A DESCARGAR\n");
					int tam = dis.readInt();
					String path = "DropBox.zip";
					String pathOrigen = rutaServer + path;
					File archivoZip = new File(path);
					crearZIP(dis, tam);
					if(!archivoZip.exists()) {
						//System.out.println("Si existeee");
						System.out.println("La path del archivo esta en: " + pathOrigen + "Con nombre: " + archivoZip.getName());
						/* Se supone que EnviarArchivo debe hacer eso XDD, entonces yo le paso esos parametros para que envie
							Declare dos en la linea 219, le envio el archivo y la path de donde esta en mi servidor
						*/
						EnviarArchivo(dis, dos, archivoZip, pathOrigen);
						// Lo elimino porque no debe estar en el servidor, solo lo hice temporalmente
						if(archivoZip.delete()) 
							System.out.println("Elimine el DropBox.zip")
					}

				}
				else if (bandera == 3) {
					//Abrir carpeta -> El servidor envia los nombres de los contenidos de la carpeta seleccionada
					int ubicacionRuta = dis.readInt();
					//Banadera = 1. Se navega dentro de una carpeta
					String nuevaRuta = "" + list[ubicacionRuta].getAbsoluteFile();
					ActualizarCliente(cl, dis, nuevaRuta, 1);
				}
				else if(bandera == 4) {
					//Subir archivos -> El servidor recibe
					String rutaDirectorio = dis.readUTF();
					String path = rutaServer + rutaDirectorio;
					File archivosRuta = new File(path);
					if(!archivosRuta.exists()) {
						archivosRuta.mkdir();
					}
				}
				else {
					System.out.println("Error al atender la solicitud del cliente.");
				}
				dis.close(); cl.close();
			}//for
		}catch(Exception e) {
			e.printStackTrace();
		}//catch
	}//main
}