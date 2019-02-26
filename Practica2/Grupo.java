import java.io.Serializable;

public class Grupo implements Serializable{
	private int Id;
	private Materia[] materias;
	private String[] profesores;
	private Horas[] horas;
	private String periodo;
	private String nombre;

	public Grupo(int Id, String periodo, String nombre){
		this.Id = Id;
		this.periodo = periodo;
		this.nombre = nombre;
		this.materias = new Materia[6];
		this.profesores = new String[6];
		this.horas = new Horas[6];
	}

	public void setMaterias(Materia materia, int i){
		this.materias[i] = materia;
	}

	public void setHoras(Horas hora, int i){
		this.horas[i] = hora;
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

	public Horas[] getHoras(){
		return this.horas;
	}

	public String getPeriodo(){
		return this.periodo;
	}

	public String getNombre(){
		return this.nombre;
	}
}