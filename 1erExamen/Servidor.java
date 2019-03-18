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
	private static String rutaServer = "./fotos/";
	private static Conexion c;

	/*********************************************************************************************
							3. RECIBIR PUBLICACION COMPLETA
	*********************************************************************************************/
	// Valor de la bandera = 3
	public static void recibirPublicacionCompleta(DataInputStream dis, String nombre) throws IOException {
		long tam = dis.readLong();
		String nombrePublicacion = dis.readUTF();
		String comentario = dis.readUTF();
		String nickname_tmp = dis.readUTF();
		int i, j;

		// Obtengo la extension de la imagen sin el nombre
		String imagen = "";
		for(i = 0; i < nombre.length(); i++) {
			if(nombre.charAt(i) == '.') {
				for(j = i; j < nombre.length(); j++) 
					imagen = imagen + nombre.charAt(j);
				break;
			}
		}		

		// CREAR PUBLICACION Y COMENTARIO

    	java.util.Date javaDate = new java.util.Date();
		long javaTime = javaDate.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);

		int idUsuario, idPublicacion, idComentario;
		Usuario usuario;
		Publicacion publicacion;
		Comentario comentarioC;
    	try {
    		c.conectarBD();
    		// Crea la nueva publicacin
	    	if(c.insertarPublicacion(nombrePublicacion, sqlTimestamp) == 1) {
	    		System.out.println(" -----> Se creo la nueva publicacion:" + nombrePublicacion);
	    		// Inserta el comentario
	    		// Consulta el id del Usuario
	    		usuario = c.obtenerIdUsuario(nickname_tmp);
	    		idUsuario = usuario.getId();
	    		// Obtiene el Id de la publicacion
	    		publicacion = c.obtenerPublicacion(nombrePublicacion);
	    		idPublicacion = publicacion.getId();
	    		// Agrego el primer comentario de la nueva Publicacion sin la imagen
	    		if(c.insertarComentario(sqlTimestamp, comentario, idUsuario, idPublicacion) == 1) {
	    			System.out.println("-----> Se Agrego el comentario a la publicacion:" + nombrePublicacion);
	    			// Obtengo el ultimo comentario
	    			comentarioC = c.obtenerComentario(usuario);
	    			if(comentarioC != null) {
		    			idComentario = comentarioC.getId();
		    			// Nuevo nombre de la imagen
		    			imagen = rutaServer + idComentario + imagen;
		    			if(c.agregarImagen(idComentario, imagen) == 1)
		    				System.out.println("Se agrego la imagen:" + imagen);
	    			}
	    		}
	    	}
    		c.cerrarConexion();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}  // Fin catch

		System.out.println("\nSe recibe el archivo " + imagen + " con " + tam + "bytes");
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(imagen)); // OutputStream
		
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

	} // RecibirComentarioCompleto

	/*********************************************************************************************
								4. RECIBIR PUBLICACION SIN IMAGEN
	*********************************************************************************************/
	// Valor de la bandera = 4
	public static void recibirPublicacion(DataInputStream dis) throws IOException {
		String nombrePublicacion = dis.readUTF();
		String comentario = dis.readUTF();
		String nickname_tmp = dis.readUTF();
	
		dis.close();
		// ********************************  
 
    	java.util.Date javaDate = new java.util.Date();
		long javaTime = javaDate.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);

		int idUsuario, idPublicacion, idComentario;
		String imagen;
		Usuario usuario;
		Publicacion publicacion;
		Comentario comentarioC;
		
    	try {
    		c.conectarBD();
    		// Crea la nueva publicacin
	    	if(c.insertarPublicacion(nombrePublicacion, sqlTimestamp) == 1) {
	    		System.out.println(" -----> Se creo la nueva publicacion:" + nombrePublicacion);
	    		// Inserta el comentario
	    		// Consulta el id del Usuario
	    		usuario = c.obtenerIdUsuario(nickname_tmp);
	    		idUsuario = usuario.getId();
	    		// Obtiene el Id de la publicacion
	    		publicacion = c.obtenerPublicacion(nombrePublicacion);
	    		idPublicacion = publicacion.getId();
	    		// Agrego el primer comentario de la nueva Publicacion sin la imagen
	    		if(c.insertarComentario(sqlTimestamp, comentario, idUsuario, idPublicacion) == 1) {
	    			System.out.println("-----> Se Agrego el comentario a la publicacion:" + nombrePublicacion);
	    		}
	    	}
    		c.cerrarConexion();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}  // Fin catch	
	} // RecibirPublicacionSinImagen

	/*********************************************************************************************
							5. RECIBIR COMENTARIO SIN IMAGEN
	*********************************************************************************************/
	// Valor de la bandera = 5
	public static void recibirComentario(DataInputStream dis) throws IOException {
		String comentario = dis.readUTF();
		int idUsuario = dis.readInt();
		int idPublicacion = dis.readInt();
		dis.close();

    	java.util.Date javaDate = new java.util.Date();
		long javaTime = javaDate.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);
	
    	try {
    		c.conectarBD();
    		// Agrego el primer comentario de la Nueva Publicacion
    		if(c.insertarComentario(sqlTimestamp, comentario, idUsuario, idPublicacion) == 1) 
    			System.out.println("Se Agrego el comentario a la publicacion:" + idPublicacion);
    		c.cerrarConexion();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}  // Fin catch	
	} // RecibirArchivos

	/*********************************************************************************************
								6. RECIBIR COMENTARIO COMPLETO
	*********************************************************************************************/
	// Valor de la bandera = 6
	public static void recibirComentarioCompleto(DataInputStream dis, String nombre) throws IOException {
		long tam = dis.readLong();
		String comentario = dis.readUTF();
		String nickname_tmp = dis.readUTF();
		int idUsuario = dis.readInt();
		int idPublicacion = dis.readInt();
		int i, j;

		// Obtengo la extension del archivo sin el nombre
		String imagen = "";
		for(i = 0; i < nombre.length(); i++) {
			if(nombre.charAt(i) == '.') {
				for(j = i; j < nombre.length(); j++) {
					imagen = imagen + nombre.charAt(j);
				}
			break;
			}
		}
		System.out.println("Nombre: " + imagen);
		
		// ********************************  
   			
    	java.util.Date javaDate = new java.util.Date();
		long javaTime = javaDate.getTime();
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp(javaTime);

		Usuario usuario;
		Comentario comentarioC;
		int idComentario;

    	try {
    		c.conectarBD();
    		// Agrego el primer comentario a la publicacion sin la imagen
    		if(c.insertarComentario(sqlTimestamp, comentario, idUsuario, idPublicacion) == 1) {
    			// Obtengo el ultimo comentario
    			usuario = c.obtenerIdUsuario(nickname_tmp);
    			comentarioC = c.obtenerComentario(usuario);
    			if(comentarioC != null) {
	    			idComentario = comentarioC.getId();
	    			// Nuevo nombre de la imagen
	    			imagen = rutaServer + idComentario + imagen;
	    			if(c.agregarImagen(idComentario, imagen) == 1)
	    				System.out.println("Se agrego la imagen:" + imagen);
    			}
    		}
    		c.cerrarConexion();
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}  // Fin catch

		System.out.println("\nSe recibe el archivo " + imagen + " con " + tam + "bytes");
		DataOutputStream dos = new DataOutputStream(new FileOutputStream(imagen)); // OutputStream
		
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

	} // RecibirComentarioCompleto


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
					// Recibe una publicación completa
					String nombre = dis.readUTF();
					recibirPublicacionCompleta(dis, nombre);
				}
				else if(bandera == 4) {
					// Recibe una nueva publicacion sin imagen
					recibirPublicacion(dis);
				}
				else if(bandera == 5) {
					// Recibe un comentario
					recibirComentario(dis);
				}
				else if(bandera == 6) {
					// Recibe un comentario con imagen
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