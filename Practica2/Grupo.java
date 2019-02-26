import java.io.Serializable;

public class Grupo implements Serializable{
	private Materia[] materias;
	private String[] profesores;
	private String[] horas;
	private String periodo;
	private String nombre;

	public Grupo(String periodo, String nombre){
		this.periodo = periodo;
		this.nombre = nombre;
	}

	public void setMaterias(Materia[] materias){
		this.materias = materias;
	}

	public void setProfesores(String[] profesores){
		this.profesores = profesores;
	}

	public void setHoras(String[] horas){
		this.horas = horas;
	}

	public Materia[] getMaterias(){
		return materias;
	}

	public String[] getProfesores(){
		return profesores;
	}

	public String[] getHoras(){
		return horas;
	}

	public String getPeriodo(){
		return periodo;
	}

	public String getNombre(){
		return nombre;
	}
}