// SERVIDOR
// Compilar: javac -cp .:mysql-connector-java-5.1.47-bin.jar: Servidor.java
// Ejecutar: java -cp .:mysql-connector-java-5.1.47-bin.jar: Servidor
import java.net.*;
import java.io.*;

public class Servidor {
	private static Conexion c;

	public static void autentificarLogin(Socket cl, DataInputStream dis) {
		try {
			// Envia y recibe objetos
			String nickname_tmp = dis.readUTF();
			String passwd_tmp = dis.readUTF();
			System.out.println("Datos recibidos: " + nickname_tmp + " " + passwd_tmp + ". Buscando...");

			c.conectarBD();
			Usuario u = c.buscarUsuario(nickname_tmp, passwd_tmp);
			c.cerrarConexion();


			if(u != null) {
				System.out.println("Objeto usuario enviado con Id: " + u.getId());
			}
			else {
				System.out.println("Usuario no encontrado, enviando null...");
			}

			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(u);
			oos.flush();
			oos.close();
			// Limpiamos memoria
			u = null;
		}
		catch(Exception e) {
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
				// Login
				if(bandera == 0) 	
					autentificarLogin(cl, dis);
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