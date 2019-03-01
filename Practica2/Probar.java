import clases.*;

public class Probar{
	public static void main(String[] args){

		/*ESTA PARTA LA HARIA EL SERVIDOR, SOLO USA SETTERS*/
		/*CARGA LOS DATOS MANUALMENTE, DESDE ARCHIVOS, ETC. Y LOS GUARDA EN MEMORIA RAM*/
		Materia[] materias = new Materia[10];
		Grupo[] grupo = new Grupo[3];
		Alumno[] alumnos = new Alumno[3];

		CargaDatos.cargar();
		materias = CargaDatos.getMaterias();
		grupo = CargaDatos.getGrupos();
		alumnos = CargaDatos.getAlumnos();

		/*------------------SE ESTAN ENVIANDO LOS OBJETOS DESDE EL SERVER---------------------*/
		/*EL CLIENTE UNICAMENTE VA A ENVIAR LA BOLETA, ID DE MATERIA, ETC (PARAMETROS, NO OBJETOS)*/

		/*ESTA PARTE SE CONSTRUYE CONFORME SE INVOCA EL METODO: inscribir()*/
		Horario horario = new Horario(6);
		Grupo[] gruposInscritos = new Grupo[6];
		
		gruposInscritos[0] = grupo[0];
		gruposInscritos[1] = grupo[0];
		gruposInscritos[2] = grupo[1];
		gruposInscritos[3] = grupo[1];
		gruposInscritos[4] = grupo[2];
		gruposInscritos[5] = grupo[2];

		for(int i = 0; i < 6; i++){
			horario.setGrupos(gruposInscritos[i], i);
			Materia[] materiasInscritas = gruposInscritos[i].getMaterias();
			horario.setMaterias(materiasInscritas[i], i);
		}


		Alumno alumno = alumnos[0];
		alumno.setHorario(horario);
		alumno.setInscripcion(true);

		/*ESTA PARTE LA HARIA EL CLIENTE, SOLO USA GETTERS*/
		/*SE CREAN NUEVOS OBJETOS EN EL CLIENTE PARA RECIBIR LOS DEL SERVER Y YA SOLO SE LEE SU INFO*/
		System.out.println("");
		System.out.println(alumno.getBoleta() + ": " + alumno.getNombreCompleto());
		if(alumno.getInscripcion()){
			System.out.println("Inscrito");
		}
		else{
			System.out.println("No inscrito.");
		}

		Horario verHorario = alumno.getHorario();
		Grupo[] verGrupos = verHorario.getGrupos(); 
		Materia[] verMaterias = verHorario.getMaterias();
		for(int i = 0; i < 6; i++){
			Grupo g = verGrupos[i]; //0
			String[] p = g.getProfesores();
			String hrs = "";
			String[][] verHoras = g.getHoras();

			for(int j = 0; j < 5; j++){
				hrs = hrs + "\t" + verHoras[i][j];
			}

			System.out.println(g.getNombre() + "\t" + verMaterias[i].getNombre() + "\t" + p[i] + "\t\t\t" + hrs);
		}
	}
}