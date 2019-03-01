// CLIENTE
import java.net.*;
import java.io.*;
import clases.*;

public class Cliente{
	private static int pto = 4321;
	private static String host = "127.0.0.1";

	public static Alumno iniciarSesion(int boleta_tmp, String psswd_temp){
		Alumno alumnoActual = null;
		
		try{
			Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //Enviar datps

			//La bandera tiene el valor de 0 = Login
			dos.writeInt(0);
			dos.flush();
			System.out.println("Enviando datos, esperando servidor...");
			//Enviar boleta y psswd de los textbox
			dos.writeInt(boleta_tmp);
			dos.flush();
			dos.writeUTF(psswd_temp);
			dos.flush();
			
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); //Recibir objetos
			//Importante hacer un cast
			alumnoActual = (Alumno) ois.readObject();
			System.out.println("Objeto recibido");

			dos.close();
			ois.close();
			cl.close();
		}catch(Exception e) {
    		e.printStackTrace();
    	}//catch

    	return alumnoActual;
	}
}