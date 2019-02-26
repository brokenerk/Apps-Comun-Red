

public class Main{
	public static void main(String[] args){

		/*ESTA PARTA LA HARIA EL SERVIDOR, SOLO USA SETTERS*/
		/*CARGA LOS DATOS MANUALMENTE, DESDE ARCHIVOS, ETC. Y LOS GUARDA EN MEMORIA RAM*/
		Materia[] materias = new Materia[10];
		String[] horas = new String[10];
		String[] prof = new String[10];
		Grupo[] grupo = new Grupo[3];
		CargaDatos.construyeObjetos(materias, horas, prof, grupo);

		/*------------------SE ESTAN ENVIANDO LOS OBJETOS DESDE EL SERVER---------------------*/
		/*EL CLIENTE UNICAMENTE VA A ENVIAR LA BOLETA, ID DE MATERIA, ETC (PARAMETROS, NO OBJETOS)*/

		

		Grupo[] gruposInscritos = new Grupo[6];
		Materia[] materiasInscritas = new Materia[6];
		/*ESTA PARTE SE CONSTRUYE CONFORME SE INVOCA EL METODO: inscribir()*/
		Horario horario = new Horario(6);
		for(int i = 0; i < 6; i++){
			gruposInscritos[i] = grupo[1];
			horario.setGrupos(gruposInscritos[i].getId(), i);

			Materia[] materiasGrupo = gruposInscritos[i].getMaterias();
			materiasInscritas[i] = materiasGrupo[i];
			horario.setMaterias(materiasInscritas[i].getId(), i);
		}

		Alumno alumno = new Alumno(2014081268, "Enrique", "Ramos", "Diaz");
		alumno.setHorario(horario);
		alumno.setInscripcion(true);

		/*ESTA PARTE LA HARIA EL CLIENTE, SOLO USA GETTERS*/
		/*SE CREAN NUEVOS OBJETOS EN EL CLIENTE PARA RECIBIR LOS DEL SERVER Y YA SOLO SE LEE SU INFO*/
		System.out.println(alumno.getBoleta() + ": " + alumno.getNombreCompleto());
		if(alumno.getInscripcion()){
			System.out.println("Inscrito");
		}
		else{
			System.out.println("No inscrito.");
		}

		Horario verHorario = alumno.getHorario();
		int[] verGrupos = verHorario.getGrupos(); // 0 0 0 0 0 0
		int[] verMaterias = verHorario.getMaterias(); //1 2 3 4 5 6

		for(int i = 0; i < 6; i++){
			Grupo g = grupo[verGrupos[i]]; //0
			int index = verMaterias[i]; //1
			Materia[] m = g.getMaterias();
			String[] p = g.getProfesores();
			Horas[] h = g.getHoras();
			String[] hrsSemana = h[i].getHoras();
			String hrs = "";


			for(int j = 0; j < hrsSemana.length; j++){
				hrs = hrs + " " +  hrsSemana[j];
			}
			System.out.println(g.getNombre() + "\t" + m[i].getNombre() + "\t\t\t" + p[i] + "\t\t\t" + hrs);
		}
	}
}