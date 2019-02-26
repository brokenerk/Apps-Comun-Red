import java.io.Serializable;

public class Materia implements Serializable{
	private int Id;
	private String nombre;
	private String[] horarioSemana;

	public Materia(int Id, String nombre){
		this.Id = Id;
		this.nombre = nombre;
		this.horarioSemana = new String[7];
	}

	public void setHorarioSemana(String[] horarioSemana){
		this.horarioSemana = horarioSemana;
	}

	public int getId(){
		return Id;
	}

	public String getNombre(){
		return nombre;
	}

}