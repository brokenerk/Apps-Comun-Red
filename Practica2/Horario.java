import java.io.Serializable;

public class Horario implements Serializable{
	private int numMaterias;
	private Materia[] materias;
	private Grupo[] grupos;
	private int[] califs;

	public Horario(int numMaterias){
		this.numMaterias = numMaterias;
	}

	/*Setters*/
	public void setMaterias(Materia[] materias){
		this.materias = materias;
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