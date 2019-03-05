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