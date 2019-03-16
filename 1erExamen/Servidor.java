// SERVIDOR
// Compilar: javac -cp ,;mysql-connector-java-5.1.47-bin.jar; Servidor.java
// Ejecutar: java -cp ,;mysql-connector-java-5.1.47-bin.jar; Servidor
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.text.SimpleDateFormat;  
import java.util.Date; 

public class Servidor {
	public static String sep = System.getProperty("file.separator");
	private static String rutaServer = "." + sep + "fotos" + sep;
	private static Conexion c;

	/*********************************************************************************************
									0. RECIBIR ARCHIVOS
	*********************************************************************************************/
	// Valor de la bandera = 0
	public static void recibirComentarioCompleto(DataInputStream dis, String nombre) throws IOException {
		long tam = dis.readLong();
		String nombrePublicacion = dis.readUTF();
		String comentario = dis.readUTF();

		//String pathDestino = dis.readUTF();
		nombre = rutaServer + nombre;
		
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
			System.out.println("\r Recibiendo el " + porciento + "% --- " + recibidos + "/" + tam + " bytes");
		} // while

		System.out.println("\nArchivo " + nombre + " de tamanio: " + tam + " recibido.");
		dos.close();
		dis.close();

		// ********************************

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
   		Date date = new Date();  
    	String fecha = formatter.format(date);
    	System.out.println(fecha);  
    	try {
    		c.conectarBD();
	    	if(c.insertarPublicacion(nombrePublicacion, fecha) == 1) {
	    		// Va a poder insertar el comentarios
	    	}
    		c.cerrarConexion();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	} // Fin catch
	} // RecibirArchivos

	public static void obtenerComentarios(Socket cl, DataInputStream dis){
		try{
			// Recibe IdPublicacion
			int IdPublicacion = dis.readInt();
			System.out.println("Datos recibidos: " + IdPublicacion + " . Buscando...");

			c.conectarBD();
			Publicacion p = c.cargarComentarios(IdPublicacion);
			c.cerrarConexion();

			if(p != null) 
				System.out.println("Objeto publicacion con comentarios enviado con Id: " + p.getId());
			else 
				System.out.println("Publicacion no encontrado, enviando null...");

			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(p);
			oos.flush();
			oos.close();
			// Limpiamos memoria
			p = null;
		}catch(Exception e) {
    		e.printStackTrace();
    	} // Fin catch
	}

	public static void obtenerPublicaciones(Socket cl){
		try{
			c.conectarBD();
			Publicacion[] p = c.recuperarPublicaciones();
			c.cerrarConexion();

			if(p != null)
				System.out.println("Publicaciones cargadas.");
			else 
				System.out.println("No existen publicaciones.");

			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(p);
			oos.flush();
			oos.close();
			// Limpiamos memoria
			p = null;
		}catch(Exception e) {
    		e.printStackTrace();
    	} // Fin catch
	}

	public static void autentificarLogin(Socket cl, DataInputStream dis) {
		try{
			// Envia y recibe objetos
			String nickname_tmp = dis.readUTF();
			String passwd_tmp = dis.readUTF();
			System.out.println("Datos recibidos: " + nickname_tmp + " " + passwd_tmp + ". Buscando...");

			c.conectarBD();
			Usuario u = c.buscarUsuario(nickname_tmp, passwd_tmp);
			c.cerrarConexion();

			if(u != null) 
				System.out.println("Objeto usuario enviado con Id: " + u.getId());
			else 
				System.out.println("Usuario no encontrado, enviando null...");

			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(u);
			oos.flush();
			oos.close();
			// Limpiamos memoria
			u = null;
		}catch(Exception e) {
    		e.printStackTrace();
    	} // Fin catch
	}

	public static void main(String[] args) {
		try {
			ServerSocket s = new ServerSocket(4321);
			s.setReuseAddress(true);
			c = new Conexion("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/", "Foro", "root", "root");
			System.out.println("Servidor Foro iniciado, esperando clientes...");

			for( ; ; ) {
				Socket cl = s.accept();
				// InputStream
				DataInputStream dis = new DataInputStream(cl.getInputStream()); 
				System.out.println("\n\nCliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());

				int bandera = dis.readInt();
				
				if(bandera == 0){
					// Login
					autentificarLogin(cl, dis);
				}
				else if(bandera == 1){
					// Actualizar
					obtenerPublicaciones(cl);
				}
				else if(bandera == 2){
					// Cargar comentarios
					obtenerComentarios(cl, dis);
				}
				else if(bandera == 3) {
					// Envia la imagen al servidor
					String nombre = dis.readUTF();
					recibirComentarioCompleto(dis, nombre);
				}
				else 
					System.out.println("Error al atender la solicitud del cliente.");
				
				dis.close();
				cl.close();
			} // Fin for
		} // Fin try
		catch(Exception e) {
			e.printStackTrace();
		} // Fin catch
	}
}