import java.io.Serializable;

public class Horario implements Serializable {
	private int numMaterias;
	/*Solo nos interesan los Ids del grupo*/
	private Materia[] materias;
	private Grupo[] grupos;
	private int[] califs;

	public Horario(int numMaterias) {
		this.numMaterias = numMaterias;
		this.materias = new Materia[this.numMaterias];
		this.grupos = new Grupo[this.numMaterias];
		this.califs = new int[this.numMaterias];
	}

	/**************************************************************************/
	/* 								SETTERS 								  */
	/**************************************************************************/
	public void setMaterias(Materia materia, int i) {
		this.materias[i] = materia;
	}

	public void setGrupos(Grupo grupo, int i) {
		this.grupos[i] = grupo;
	}

	public void setCalifs(int calif, int i) {
		this.califs[i] = calif;
	}

	/**************************************************************************/
	/* 								GETTERS 								  */
	/**************************************************************************/
	public Materia[] getMaterias() {
		return materias;
	}

	public int getNumMaterias() {
		return numMaterias;
	}

	public Grupo[] getGrupos() {
		return grupos;
	}

	public int[] getCalifs() {
		return califs;
	}

}