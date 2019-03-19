// CLIENTE
import java.net.*;
import java.io.*;

public class Cliente {
	private static int pto = 4444;
	private static String host = "127.0.0.1";
	public static Grupo[] grupos = null;

	public static Alumno iniciarSesion(int boleta_tmp, String psswd_temp) {
		Alumno alumnoActual = null;
		
		try {
			Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //Enviar datos

			// Bandera con valor 0 = Login
			dos.writeInt(0);
			dos.flush();
			System.out.println("Enviando datos: " + boleta_tmp + " - " + psswd_temp + ", esperando servidor...");

			// Enviar boleta y psswd de los textbox
			dos.writeInt(boleta_tmp);
			dos.flush();
			dos.writeUTF(psswd_temp);
			dos.flush();

			// Recibir objetos
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); 
			
			// Importante hacer un cast
			alumnoActual = (Alumno) ois.readObject();
			System.out.println("Objeto recibido");

			dos.close();
			ois.close();
			cl.close();
		}
		catch(Exception e) {
    		e.printStackTrace();
    	} // Catch

    	return alumnoActual;
	}

	public static void obtenerGrupos() {
		try {
			Socket cl = new Socket(host, pto);
 			// Enviar datos
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream());

			// La bandera tiene el valor de 1 = getGrupos
			dos.writeInt(1);
			dos.flush();

			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); //Recibir objetos

			grupos = (Grupo[]) ois.readObject();
			System.out.println("Grupos recibidos");

			ois.close();
			cl.close();

		}catch(Exception e) {
    		e.printStackTrace();
    	}//catch
	}

	public static void enviarHorario(String[] grupos, String[] materias, int boleta) {
		try {
			Socket cl = new Socket(host, pto);

			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //Enviar datps

			// La bandera tiene el valor de 2 = enviarHorario
			dos.writeInt(2);
			dos.flush();

			// Enviamos el numero de boleta
			dos.writeInt(boleta);
			dos.flush();
			System.out.println("Boleta enviada");
			// Enviamos los arreglos de materias y grupos seleccionados
			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(grupos);
			oos.flush();
			System.out.println("Grupos seleccionados enviados");
			oos.writeObject(materias);
			oos.flush();
			System.out.println("Materias seleccionadas enviadas");
			dos.close();
			oos.close();
			cl.close();
		}
		catch(Exception e) {
    		e.printStackTrace();
    	}//catch
	}
}