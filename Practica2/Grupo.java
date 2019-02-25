public class Grupo{
	private Materia[] materias;
	private String[] profesores;
	private int numMaterias = 6; /*Constante*/
	private String periodo;
	private String nombre;

	public Grupo(String periodo, String nombre){
		materias = new Materia[numMaterias];
		profesores = new Profesor[numMaterias];
		this.periodo = periodo;
		this.nombre = nombre;
	}

	public void setMaterias(Materia[] materias){
		this.materias = materias;
	}

	public void setProfesores(String[] profesores){
		this.profesores = profesores;
	}

	public Materia[] getMaterias(){
		return materias;
	}

	public String[] getProfesores(){
		return profesores;
	}

	public String getPeriodo(){
		return periodo;
	}

	public String getNombre(){
		return nombre;
	}
}