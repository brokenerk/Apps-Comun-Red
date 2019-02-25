public class Profesor{
	private String nombre;
	private Grupo[] grupos;
	private int numGrupos;
	private Materia[] materias;
	private int numMaterias;

	public Profesor(String nombre, int numGrupos, int numMaterias){
		this.nombre = nombre;
		this.numGrupos = numGrupos;
		grupos = new Grupo[this.numGrupos];
		this.numMaterias = numMaterias;
		materias = new Materia[this.numMaterias];
	}

	/*SETTERS*/
	public void setGrupos(Grupo[] grupos){
		this.grupos = grupos;
	}

	public void setMaterias(Materia[] materias){
		this.materias = materias;
	}

	/*GETTERS*/
	public String getNombre(){
		return nombre;
	}

	public Grupo[] getGrupos(){
		return grupos;
	}

	public int getNumGrupos(){
		return numGrupos;
	}

	public int getNumMaterias(){
		return numMaterias;
	}

	public Materia[] getMaterias(){
		return materias;
	}
}