public class Horario{
	private int numMaterias;
	private Materia[] materias;
	private String[] horas;
	private Grupo[] grupos;
	private int[] califs;

	public Horario(int numMaterias){
		this.numMaterias = numMaterias;
		materias = new Materia[this.numMaterias];
		horas = new String[this.numMaterias];
		grupos = new Grupo[this.numMaterias];
		califs = new int[this.numMaterias];
	}

	/*Setters*/
	public void setMaterias(Materia[] materias){
		this.materias = materias;
	}

	public void setHoras(String[] horas){
		this.horas = horas;
	}

	public void setGrupos(Grupo[] grupos){
		this.grupos = grupos;
	}

	public void setCalifs(int[] califs){
		this.califs = califs;
	}

	/*Getters*/
	public Materia[] getMaterias(){
		return materias;
	}

	public String[] getHoras(){
		return horas;
	}

	public int getNumMaterias(){
		return numMaterias;
	}

	public Grupo[] getGrupos(){
		return grupos;
	}

	public int[] getCalifs(){
		return califs;
	}

}