public class Comentario{
	private int Id;
	private String fecha;
	private String texto;
	private ImageIcon imagen;
	private Usuario usuario;

	public Comentario(int Id, String fecha, String texto, String imagen, Usuario usuario){
		this.Id = Id;
		this.fecha = fecha;
		this.texto = texto;
		this.imagen = new ImageIcon(imagen);
		this.usuario = usuario;
	}

	public int getId(){
		return this.Id;
	}

	public String getFecha(){
		return this.fecha;
	}

	public String getTexto(){
		return this.texto;
	}

	public ImageIcon getImagen(){
		return this.imagen;
	}

	public Usuario getUsuario(){
		return this.usuario;
	}
}