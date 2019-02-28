import java.io.Serializable;

public class Grupo implements Serializable{
	private int Id;
	private Materia[] materias;
	private String[] profesores;
	private String[][] horas;
	private String periodo;
	private String nombre;

	public Grupo(int Id, String periodo, String nombre){
		this.Id = Id;
		this.periodo = periodo;
		this.nombre = nombre;
		this.materias = new Materia[6]; 
		this.profesores = new String[6];
		this.horas = new String[6][5]; //6 materias, 5 dias a la semana

		for(int i = 0; i < 6; i++){
			for(int j = 0; j < 5; j++){
				this.horas[i][j] = "";
			}
		}
	}

	public void setMaterias(Materia materia, int i){
		this.materias[i] = materia;
	}

	public void setHoras(String hora, int i, int j){
		this.horas[i][j] = hora;
	}

	public void setProfesores(String profesor, int i){
		this.profesores[i] = profesor;
	}

	public int getId(){
		return this.Id;
	}

	public Materia[] getMaterias(){
		return this.materias;
	}

	public String[] getProfesores(){
		return this.profesores;
	}

	public String[][] getHoras(){
		return this.horas;
	}

	public String getPeriodo(){
		return this.periodo;
	}

	public String getNombre(){
		return this.nombre;
	}
}