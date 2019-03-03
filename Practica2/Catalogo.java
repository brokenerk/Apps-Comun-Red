import java.util.*;

public class Catalogo {
	//Cargamos toda la info para trabajar
	static Materia[] materias = new Materia[10];
	static String[] horas = new String[9];
	static String[] prof = new String[10];
	static Grupo[] grupo = new Grupo[3];
	static Alumno[] alumnos = new Alumno[5];

	public static Materia[] getMaterias(){
		return materias;
	}

	public static Grupo[] getGrupos(){
		return grupo;
	}

	public static Alumno[] getAlumnos(){
		return alumnos;
	}

	public static void cargar(){
		/* Se cargan 10 materias */
		materias[0] = new Materia(0, "Quimica VI");
		materias[1] = new Materia(1, "Web Application Development");
		materias[2] = new Materia(2, "Instrumentacion");
		materias[3] = new Materia(3, "Ingenieria Software");
		materias[4] = new Materia(4, "Matematicas Avanzadas para la Ingenieria");
		materias[5] = new Materia(5, "Sistemas Operativos");
		materias[6] = new Materia(6, "Tecnologias para la Web");
		materias[7] = new Materia(7, "Analisis de Algoritmos");
		materias[8] = new Materia(8, "Calculo Aplicado");
		materias[9] = new Materia(9, "Aplicaciones para Comunicaciones de Red");

		/*Se cargan alumnos*/
		alumnos[0] = new Alumno(2014081268, "prueba123", "Enrique", "Ramos", "Diaz");
		alumnos[1] = new Alumno(2014081144, "prueba123", "Angel", "Hernandez", "Molina");
		alumnos[2] = new Alumno(2014171285, "prueba123", "Abigail", "Nicolas", "Sayago");
		alumnos[3] = new Alumno(2015674078, "prueba123", "Oribe", "Peralta", "Morones");
		alumnos[4] = new Alumno(2014011111, "prueba123", "Son", "Goku", "");

		alumnos[0].setFoto("./fotos/" + alumnos[0].getBoleta() + ".png");
		alumnos[1].setFoto("./fotos/default.png");
		alumnos[2].setFoto("./fotos/" + alumnos[2].getBoleta() + ".png");
		alumnos[3].setFoto("./fotos/" + alumnos[3].getBoleta() + ".png");
		alumnos[4].setFoto("./fotos/" + alumnos[4].getBoleta() + ".png");

		alumnos[0].setInscripcion(false);
		alumnos[1].setInscripcion(false);
		alumnos[2].setInscripcion(false);


		/* Se cargan las horas matutinas y vespertinas */
		horas[0] = "7:00 - 8:30";
		horas[1] = "8:30 - 10:00";
		horas[2] = "10:30 - 12:00";
		horas[3] = "12:00 - 13:30";
		horas[4] = "13:30 - 15:00";
		horas[5] = "15:00 - 16:30";
		horas[6] = "16:30 - 18:00";
		horas[7] = "18:00 - 19:30";
		horas[8] = "19:30 - 21:00";

		/* Se cargan los profesores para cada materia */
		prof[0] = "Copca Ramirez Vargas";
		prof[1] = "Hermes Francisco Montes Casiano";
		prof[2] = "Juan Carlos Tellez Barrera";
		prof[3] = "Jose Jaime Lopez Rabadan";
		prof[4] = "Ignacio Rios de la Torre";
		prof[5] = "Jorge Cortes Galicia";
		prof[6] = "Jose Antonio Ramirez";
		prof[7] = "Edgardo Adrían Franco Martínez";
		prof[8] = "Hector Rojas Luna";
		prof[9] = "Axel Ernesto Moreno Cervantes"; 

		grupo[0] = new Grupo(0, "2019-2", "3CM8");
		grupo[1] = new Grupo(1, "2019-2", "3CM9");
		grupo[2] = new Grupo(2, "2019-2", "3CV8");

		//System.out.println("Grupo: " + grupo[0].getNombre());
		for(int i = 0; i < 6; i++) {
			//System.out.println(materias[i].getNombre() + " " + prof[i] + " ");
			grupo[0].setMaterias(materias[i], i);
			grupo[0].setProfesores(prof[i], i);
		}

		//System.out.println("");
		//System.out.println("Grupo: " + grupo[1].getNombre());
		int z = 0;

		for(int i = 2; i < 8; i++) {
			//System.out.println(materias[i].getNombre() + " " + prof[i] + " ");
			grupo[1].setMaterias(materias[i], z);
			grupo[1].setProfesores(prof[i], z);
			z++;
		}

		
		//System.out.println("");
		//System.out.println("Grupo: " + grupo[2].getNombre());
		z = 0;

		for(int i = 4; i < 10; i++) {
			//System.out.println(materias[i].getNombre() + " " + prof[i] + " ");
			grupo[2].setMaterias(materias[i], z);
			grupo[2].setProfesores(prof[i], z);
			z++;
		}


		for(int x = 0; x < 2; x++) {
			grupo[x].setHoras(horas[0], 0, 0);
			grupo[x].setHoras(horas[0], 0, 3);
			grupo[x].setHoras(horas[1], 0, 4);

			grupo[x].setHoras(horas[0], 1, 1);
			grupo[x].setHoras(horas[0], 1, 2);
			grupo[x].setHoras(horas[0], 1, 4);

			grupo[x].setHoras(horas[1], 2, 0);
			grupo[x].setHoras(horas[1], 2, 2);
			grupo[x].setHoras(horas[1], 2, 3);

			grupo[x].setHoras(horas[2], 3, 0);
			grupo[x].setHoras(horas[1], 3, 1);
			grupo[x].setHoras(horas[2], 3, 3);

			grupo[x].setHoras(horas[3], 4, 0);
			grupo[x].setHoras(horas[3], 4, 2);
			grupo[x].setHoras(horas[3], 4, 3);
			grupo[x].setHoras(horas[3], 4, 4);

			grupo[x].setHoras(horas[2], 5, 1);
			grupo[x].setHoras(horas[2], 5, 2);
			grupo[x].setHoras(horas[2], 5, 4);
		}

			grupo[2].setHoras(horas[4], 0, 0);
			grupo[2].setHoras(horas[5], 0, 1);
			grupo[2].setHoras(horas[5], 0, 2);
			grupo[2].setHoras(horas[5], 0, 4);

			grupo[2].setHoras(horas[4], 1, 1);
			grupo[2].setHoras(horas[4], 1, 2);
			grupo[2].setHoras(horas[4], 1, 3);

			grupo[2].setHoras(horas[5], 2, 0);
			grupo[2].setHoras(horas[5], 2, 3);
			grupo[2].setHoras(horas[6], 2, 4);

			grupo[2].setHoras(horas[7], 3, 0);
			grupo[2].setHoras(horas[7], 3, 2);
			grupo[2].setHoras(horas[7], 3, 3);

			grupo[2].setHoras(horas[8], 4, 0);
			grupo[2].setHoras(horas[7], 4, 1);
			grupo[2].setHoras(horas[8], 4, 3);

			grupo[2].setHoras(horas[8], 5, 1);
			grupo[2].setHoras(horas[8], 5, 2);
			grupo[2].setHoras(horas[8], 5, 4);


	}
}