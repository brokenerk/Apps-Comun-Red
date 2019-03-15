import java.util.ArrayList;
import java.io.Serializable;

public class Publicacion implements Serializable{
	private int Id;
	private ArrayList<Comentario> comentarios;
	private String nombre;
	private String fecha;

	public Publicacion(int Id, String nombre, String fecha){
		this.Id = Id;
		this.comentarios = new ArrayList<Comentario>();
		this.nombre = nombre;
		this.fecha = fecha;
	}

	public int getId(){
		return this.Id;
	}

	public ArrayList<Comentario> getComentarios(){
		return this.comentarios;
	}

	public String getFecha(){
		return this.fecha;
	}

	public String getNombre(){
		return this.nombre;
	}

	public void setComentario(int IdComentario, Comentario nvoComentario){
		this.comentarios.add(IdComentario, nvoComentario);
	}
}