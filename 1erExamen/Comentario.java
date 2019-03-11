public class Comentario{
	private int Id;
	private String fecha;
	private String texto;
	private ImageIcon imagen;

	public Comentario(int Id, String fecha, String texto, String imagen){
		this.Id = Id;
		this.fecha = fecha;
		this.texto = texto;
		this.imagen = new ImageIcon(imagen + ".png");
	}

	public 
}