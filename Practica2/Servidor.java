// SERVIDOR
import java.net.*;
import java.io.*;
import clases.*;

public class Servidor{
	//Clases para trabajar
	static Materia[] materias = new Materia[10];
	static Grupo[] grupos = new Grupo[3];
	static Alumno[] alumnos = new Alumno[3];


	public static void AutentificarLogin(Socket cl, DataInputStream dis){
		try{
			/*Para enviar y recibir objetos*/
			int boleta_temp = dis.readInt();
			String passwd_temp = dis.readUTF();
			System.out.println("Datos recibidos: " + boleta_temp + " " + passwd_temp + ". Buscando...");

			boolean existe = false;
			int numReg = 0;
			Alumno alumnoActual = null;

			for(int i = 0; i < 3; i++){
				int b = alumnos[i].getBoleta();
				String p = alumnos[i].getContrasenia();

				if(boleta_temp == b && passwd_temp.equals(p)){
					existe = true;
					numReg = i;
				}
			}

			if(existe){
				alumnoActual = alumnos[numReg];
				System.out.println("Objeto alumno enviado con boleta: " + alumnoActual.getBoleta());
			}
			else{
				System.out.println("Alumno no encontrado, enviando null...");
			}

			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(alumnoActual);
			oos.flush();
			oos.close();
			//Limpiamos memoria
			alumnoActual = null;

		}catch(Exception e) {
    		e.printStackTrace();
    	}//catch
		
	}



	public static void main(String[] args){
		//Construimos nuestros catalogos
		CargaDatos.cargar();
		materias = CargaDatos.getMaterias();
		grupos = CargaDatos.getGrupos();
		alumnos = CargaDatos.getAlumnos();

		System.out.println("Catalogos creados.");
		try{
			ServerSocket s = new ServerSocket(4321);
			s.setReuseAddress(true);
			System.out.println("Servidor Mini SAES iniciado, esperando alumnos/clientes...");

			for( ; ; ){
				Socket cl = s.accept();
				DataInputStream dis = new DataInputStream(cl.getInputStream()); // InputStream
				System.out.println("\n\nCliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());

				int bandera = dis.readInt();

				if(bandera == 0){
					//Login
					AutentificarLogin(cl, dis);
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
	}
}