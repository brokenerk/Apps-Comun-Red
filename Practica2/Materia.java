public class Materia{
	private int Id;
	private String nombre;

	public Materia(int Id, String nombre){
		this.Id = Id;
		this.nombre = nombre;
	}

	public int getId(){
		return Id;
	}

	public String getNombre(){
		return nombre;
	}
}