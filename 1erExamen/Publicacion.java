public class Publicacion{
	private int Id;
	private Comentario[] comentarios;
	private String nombre;
	private String fecha;

	public Publicacion(int Id, Comentario[] comentarios, String nombre, String fecha){
		this.Id = Id;
		this.comentarios = Arrays.copyOf(comentarios, comentarios.length);
		this.nombre = nombre;
		this.fecha = fecha;
	}

	public int getId(){
		return this.Id;
	}

	public Comentario[] getComentarios(){
		return this.comentarios;
	}

	public String getFecha(){
		return this.fecha;
	}

	public String getNombre(){
		return this.nombre;
	}
}