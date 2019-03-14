// CLIENTE
import java.net.*;
import java.io.*;

public class Cliente {
	private static int pto = 4321;
	private static String host = "127.0.0.1";

	public static Usuario iniciarSesion(String nickname_tmp, String psswd_tmp) {
		Usuario usuarioActual = null;
		
		try {
			Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //Enviar datos

			// Bandera con valor 0 = Login
			dos.writeInt(0);
			dos.flush();
			System.out.println("Enviando datos: " + nickname_tmp + " - " + psswd_tmp + ", esperando servidor...");

			// Enviar boleta y psswd de los textbox
			dos.writeUTF(nickname_tmp);
			dos.flush();
			dos.writeUTF(psswd_tmp);
			dos.flush();

			// Recibir objetos
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); 
			
			// Importante hacer un cast
			usuarioActual = (Usuario) ois.readObject();
			//System.out.println("Objeto recibido");

			dos.close();
			ois.close();
			cl.close();
		}
		catch(Exception e) {
    		e.printStackTrace();
    	} // Catch

    	return usuarioActual;
	}

	
}