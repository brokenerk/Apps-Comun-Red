import java.io.Serializable;

public class Materia implements Serializable{
	private int Id;
	private String nombre;
	

	public Materia(int Id, String nombre){
		this.Id = Id;
		this.nombre = nombre;
	}

	public int getId(){
		return this.Id;
	}

	public String getNombre(){
		return this.nombre;
	}
}