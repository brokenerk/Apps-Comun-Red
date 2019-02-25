public class Main{
	public static void main(String[] args){

		Materia[] materias = new Materia[6];
		materias[0] = new Materia(1, "Sistemas Operativos");
		materias[1] = new Materia(2, "Redes de computadoras");
		materias[2] = new Materia(3, "Ingenieria Software");
		materias[3] = new Materia(4, "Instrumentacion");
		materias[4] = new Materia(5, "Mates Avanzadas");
		materias[5] = new Materia(6, "Quimica");

		Profesor profGalicia = new Profesor("Jorge Cortes Galicia", 1, 6);
		profGalicia.setMaterias(materias);

		Alumno alumno = new Alumno(2014081268, "Enrique", "Ramos", "Diaz");

		Horario horario = new Horario(6);
		horario.setMaterias(materias);


		Grupo[] grupo = new Grupo[1];
		grupo[0] = new Grupo("2019-2", horario, "3CM8");

		profGalicia.setGrupos(grupo);
		horario.setGrupos(grupo);
	
		alumno.setHorario(horario);

	}
}