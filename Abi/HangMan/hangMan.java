// SERVIDOR
import java.net.*;
import java.io.*;

public class Servidor {
	// Clases para trabajar
	static String[] easy = new String[5];
	static String[] normal = new String[5];
	static String[] hard = new String[5];


	public static void main(String[] args) {
		easy = {"sol", "sergio", "enrique", "oso", "abigail"};
		normal = {"computadora", "enriquecedor", "maravilloso", "baloncesto", "aplicaciones"};
		easy = {"opciones de socket", "sergio es un jugador de escom", "introduccion a javascript", "aplicaciones en red", "gran club de algoritmia"};

		System.out.println("-----> Lista de palabras cargado.");
		try {
			ServerSocket s = new ServerSocket(4321);
			s.setReuseAddress(true);
			System.out.println("-----> SERVIDOR INICIADO");

			for( ; ; ) {
				Socket cl = s.accept();
				// InputStream
				DataInputStream dis = new DataInputStream(cl.getInputStream()); 
				System.out.println("\n\n -----> Cliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());

				int bandera = dis.readInt();
				// Set a word EASY
				if(bandera == 0) 	
					autentificarLogin(cl, dis);
				
				// Set a word NORMAL
				else if(bandera == 1)
					enviarGrupos(cl);
				
				// Set a word HARD
				else if(bandera == 2)
					recibirHorario(cl, dis);
				
				// Verify the letter
				else if(bandera == 3)
				
				else 
					System.out.println("-----> ERROR: Unknown request");
				
				dis.close();
				cl.close();
			} // Fin for
		} // Fin try
		catch(Exception e) {
			e.printStackTrace();
		} // Fin catch
	}
}