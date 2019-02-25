public class Grupo{
	private Materia[] materias;
	private Profesor[] profesores;
	private int numMaterias = 6; /*Constante*/
	private String periodo;
	private Horario horario;
	private String nombre;

	public Grupo(String periodo, Horario horario, String nombre){
		materias = new Materia[numMaterias];
		profesores = new Profesor[numMaterias];
		this.periodo = periodo;
		this.horario = horario;
		this.nombre = nombre;
	}

	public void setMaterias(Materia[] materias){
		this.materias = materias;
	}

	public void setProfesores(Profesor[] profesores){
		this.profesores = profesores;
	}

	public Materia[] getMaterias(){
		return materias;
	}

	public Profesor[] getProfesores(){
		return profesores;
	}

	public String getPeriodo(){
		return periodo;
	}

	public Horario getHorario(){
		return horario;
	}

	public String getNombre(){
		return nombre;
	}
}