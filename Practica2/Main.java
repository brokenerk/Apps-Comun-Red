

public class Main{
	public static void main(String[] args){

		/*ESTA PARTA LA HARIA EL SERVIDOR, SOLO USA SETTERS*/
		/*CARGA LOS DATOS MANUALMENTE, DESDE ARCHIVOS, ETC. Y LOS GUARDA EN MEMORIA RAM*/
		Materia[] materias = new Materia[10];
		String[] horas = new String[10];
		String[] prof = new String[10];
		Grupo[] grupo = new Grupo[3];
		CargaDatos.construyeObjetos(materias, horas, prof, grupo);

		
		


		/*ESTA PARTE SE CONSTRUYE CONFORME SE INVOCA EL METODO: inscribir()*/
		Horario horario = new Horario(6);
		//horario.setMaterias(materias);
		horario.setGrupos(grupo);

		Alumno alumno = new Alumno(2014081268, "Enrique", "Ramos", "Diaz");
		alumno.setHorario(horario);
		alumno.setInscripcion(true);

		/*------------------SE ESTAN ENVIANDO LOS OBJETOS DESDE EL SERVER---------------------*/
		/*EL CLIENTE UNICAMENTE VA A ENVIAR LA BOLETA, ID DE MATERIA, ETC (PARAMETROS, NO OBJETOS)*/

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
		//Materia[] verMaterias = verHorario.getMaterias();
		Grupo[] verGrupos = verHorario.getGrupos();
		Grupo verGrupo = verGrupos[0];
		Materia[] verMaterias = verGrupo.getMaterias();

		System.out.println("Grupo: " + verGrupo.getNombre());

		for(int i = 0; i < 6; i++){
			//String[] verHoras = verGrupo.getHoras();
			String[] verProfesores = verGrupo.getProfesores();
			System.out.println(verMaterias[i].getNombre() + " ----- " + verProfesores[i]);
		}
	}
}