import java.io.Serializable;

public class Horario implements Serializable{
	private int numMaterias;
	/*Solo nos interesan los Ids del grupo y de las materias*/
	private int[] IdMaterias;
	private int[] IdGrupos;
	private int[] califs;

	public Horario(int numMaterias){
		this.numMaterias = numMaterias;
		this.IdMaterias = new int[this.numMaterias];
		this.IdGrupos = new int[this.numMaterias];
		this.califs = new int[this.numMaterias];
	}

	/*Setters*/
	public void setMaterias(int IdMateria, int i){
		this.IdMaterias[i] = IdMateria;
	}

	public void setGrupos(int IdGrupo, int i){
		this.IdGrupos[i] = IdGrupo;
	}

	public void setCalifs(int calif, int i){
		this.califs[i] = calif;
	}

	/*Getters*/
	public int[] getMaterias(){
		return IdMaterias;
	}

	public int getNumMaterias(){
		return numMaterias;
	}

	public int[] getGrupos(){
		return IdGrupos;
	}

	public int[] getCalifs(){
		return califs;
	}

}