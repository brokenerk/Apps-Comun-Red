public class Publicacion{
	private Comentario[] comentarios;
	private String nombre;
	private String fecha;

	public Publicacion(Comentario[] comentarios, String nombre, String fecha){
		this.comentarios = Arrays.copyOf(comentarios, comentarios.length);
		this.nombre = nombre;
		this.fecha = fecha;
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