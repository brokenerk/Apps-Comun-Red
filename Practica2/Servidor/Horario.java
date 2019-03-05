import java.io.Serializable;

public class Horario implements Serializable {
	private int numMaterias;
	/*Solo nos interesan los Ids del grupo*/
	private Materia[] materias;
	private Grupo[] grupos;
	private int[] califs;
	private String[] profesores;
	private String[][] horas;

	public Horario(int numMaterias) {
		this.numMaterias = numMaterias;
		this.materias = new Materia[this.numMaterias];
		this.grupos = new Grupo[this.numMaterias];
		this.califs = new int[this.numMaterias];
		this.profesores = new String[this.numMaterias];
		this.horas = new String[this.numMaterias][5]; // N materias, 5 dias a la semana

		for(int i = 0; i < this.numMaterias; i++) {
			for(int j = 0; j < 5; j++) {
				this.horas[i][j] = "";
			}
		}
	}

	/**********************************************/
	/* 				SETTERS 				     */
	/********************************************/
	public void setMaterias(Materia materia, int i) {
		this.materias[i] = materia;
	}

	public void setHoras(String hora, int i, int j) {
		this.horas[i][j] = hora;
	}

	public void setProfesores(String profesor, int i) {
		this.profesores[i] = profesor;
	}

	public void setGrupos(Grupo grupo, int i) {
		this.grupos[i] = grupo;
	}

	public void setCalifs(int calif, int i) {
		this.califs[i] = calif;
	}

	/*******************************************/
	/* 					GETTERS 	 			*/
	/*******************************************/
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

	public String[] getProfesores() {
		return this.profesores;
	}

	public String[][] getHoras() {
		return this.horas;
	}

}