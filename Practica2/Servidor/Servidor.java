// SERVIDOR
import java.net.*;
import java.io.*;

public class Servidor {
	// Clases para trabajar
	static Materia[] materias = new Materia[10];
	static Grupo[] grupos = new Grupo[3];
	static Alumno[] alumnos = new Alumno[5];

	public static void autentificarLogin(Socket cl, DataInputStream dis) {
		try {
			// Envia y recibe objetos
			int boleta_temp = dis.readInt();
			String passwd_temp = dis.readUTF();
			System.out.println("Datos recibidos: " + boleta_temp + " " + passwd_temp + ". Buscando...");

			boolean existe = false;
			int numReg = 0;
			Alumno alumnoActual = null;

			for(int i = 0; i < alumnos.length; i++) {
				int b = alumnos[i].getBoleta();
				String p = alumnos[i].getContrasenia();

				// Para el inicio de sesión
				if(boleta_temp == b && passwd_temp.equals(p)) {
					existe = true;
					numReg = i;
				}
			}

			if(existe) {
				alumnoActual = alumnos[numReg];
				System.out.println("Objeto alumno enviado con boleta: " + alumnoActual.getBoleta());
			}
			else {
				System.out.println("Alumno no encontrado, enviando null...");
			}

			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(alumnoActual);
			oos.flush();
			oos.close();
			// Limpiamos memoria
			alumnoActual = null;
		}
		catch(Exception e) {
    		e.printStackTrace();
    	} // Fin catch
	}

	public static void enviarGrupos(Socket cl) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(grupos);
			oos.flush();

			System.out.println("Grupos enviados");
			oos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void recibirHorario(Socket cl, DataInputStream dis) {
		try {
			int boletaActual = dis.readInt();
			String[] g;
			String[] m;

			// Recibir objetos
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); 
			g = (String[]) ois.readObject();
			m = (String[]) ois.readObject();
			
			int numMateriasInscritas = m.length;
			int inscripcion = 0;

			Horario horarioInscrito = new Horario(numMateriasInscritas);
			
			for(int i = 0; i < alumnos.length; i++) {
				if(alumnos[i].getBoleta() == boletaActual)
					inscripcion = i;
			}

			System.out.println("Horario recibido:");

			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < numMateriasInscritas; j++) {
					if(grupos[i].getNombre().equals(g[j])) {
						System.out.print(grupos[i].getNombre() + " - ");
						horarioInscrito.setGrupos(grupos[i], j);

						Materia[] mGrupo = grupos[i].getMaterias();
						String[] profsInscritos = grupos[i].getProfesores();
						String[][] horasInscritas = grupos[i].getHoras(); 

						for(int k = 0; k < 6; k++) {
							if(mGrupo[k].getNombre().equals(m[j])) {

								System.out.print(mGrupo[k].getNombre() + " - ");
								horarioInscrito.setMaterias(mGrupo[k], j);
								horarioInscrito.setProfesores(profsInscritos[k], j);
								System.out.print(profsInscritos[k] + " - ");

								String hrs = "";

								for(int dias = 0; dias < 5; dias++) {
									horarioInscrito.setHoras(horasInscritas[k][dias], j, dias);
									hrs = hrs + " " + horasInscritas[k][dias];
								}
								System.out.println(hrs);	
							}
						}
					}
					// Genera calificaciones aleatorias a modo de ejemplo.
					// Rango de 5 a 10
					int calif = (int) (Math.random() * 6) + 5;
					horarioInscrito.setCalifs(calif, j);
				}
			}

			System.out.println("Construyendo horario... Listo.");
			System.out.println("Asignando horario a alumno.");

			alumnos[inscripcion].setHorario(horarioInscrito);
			alumnos[inscripcion].setInscripcion(true);

			System.out.println("Alumno inscrito con horario correctamente.");
			ois.close();
			cl.close();	
		}
		catch(Exception e) {
			e.printStackTrace();
		} // Fin catch	
	}

	public static void main(String[] args) {
		// Construimos nuestros catalogos
		Catalogo.cargar();
		materias = Catalogo.getMaterias();
		grupos = Catalogo.getGrupos();
		alumnos = Catalogo.getAlumnos();

		System.out.println("Catalogos creados.");
		try {
			ServerSocket s = new ServerSocket(4444);
			s.setReuseAddress(true);
			System.out.println("Servidor Mini SAES iniciado, esperando alumnos/clientes...");

			for( ; ; ) {
				Socket cl = s.accept();
				// InputStream
				DataInputStream dis = new DataInputStream(cl.getInputStream()); 
				System.out.println("\n\nCliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());

				int bandera = dis.readInt();
				// Login
				if(bandera == 0) 	
					autentificarLogin(cl, dis);
				
				// Enviar grupos con materias
				else if(bandera == 1)
					enviarGrupos(cl);
				
				// Recibir grupos y materias para horario
				else if(bandera == 2)
					recibirHorario(cl, dis);
				
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