public class Clases {
	public static void main(String[] args) {

		// El servidor se encarga de esta parte, usa SETTERS
		// Carga datos manualmente, puede ser archivo, etc. Se guarda en memoria RAM
		Materia[] materias = new Materia[10];
		Grupo[] grupo = new Grupo[3];
		Alumno[] alumnos = new Alumno[3];

		Catalogo.cargar();
		materias = Catalogo.getMaterias();
		grupo = Catalogo.getGrupos();
		alumnos = Catalogo.getAlumnos();

		// ---------------------------------------------------------------------------
		//						ENVIAR OBJETOS DESDE EL SERVIDOR
		// ---------------------------------------------------------------------------
		// El cliente envia boleta, ID de materia (Parametros, no objetos)
		// Esta parte se construye conforme se invoca el metodo: inscribir

		Horario horario = new Horario(6);
		Grupo[] gruposInscritos = new Grupo[6];
		
		gruposInscritos[0] = grupo[0];
		gruposInscritos[1] = grupo[0];
		gruposInscritos[2] = grupo[1];
		gruposInscritos[3] = grupo[1];
		gruposInscritos[4] = grupo[2];
		gruposInscritos[5] = grupo[2];

		for(int i = 0; i < 6; i++) {
			horario.setGrupos(gruposInscritos[i], i);
			Materia[] materiasInscritas = gruposInscritos[i].getMaterias();
			horario.setMaterias(materiasInscritas[i], i);
		}

		Alumno alumno = alumnos[0];
		alumno.setHorario(horario);
		alumno.setInscripcion(true);

		// El cliente se encarga de esta parte, usa GETTERS
		// Crea nuevos objetos en el cliente para recibir los del Servidor
		// Se lee informacion		
		System.out.println("");
		System.out.println(alumno.getBoleta() + ": " + alumno.getNombreCompleto());
		if(alumno.getInscripcion())
			System.out.println("Inscrito");
		else
			System.out.println("No inscrito.");

		Horario verHorario = alumno.getHorario();
		Grupo[] verGrupos = verHorario.getGrupos(); 
		Materia[] verMaterias = verHorario.getMaterias();
		for(int i = 0; i < 6; i++) {
			Grupo g = verGrupos[i]; //0
			String[] p = g.getProfesores();
			String hrs = "";
			String[][] verHoras = g.getHoras();

			for(int j = 0; j < 5; j++) {
				hrs = hrs + "\t" + verHoras[i][j];
			}
			System.out.println(g.getNombre() + "\t" + verMaterias[i].getNombre() + "\t" + p[i] + "\t\t\t" + hrs);
		}
	}
}