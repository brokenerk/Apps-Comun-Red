import java.io.Serializable;

public class Alumno implements Serializable {
	private int boleta;
	private String nombre;
	private String primerAp;
	private String segundoAp;
	private Horario horario;
	private boolean inscripcion;
	private ImageIcon foto;

	public Alumno(int boleta, String nombre, String primerAp, String segundoAp) {
		this.boleta = boleta;
		this.nombre = nombre;
		this.primerAp = primerAp;
		this.segundoAp = segundoAp;
	}

	/**************************************************************************/
	/* 								SETTERS 								  */
	/**************************************************************************/

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	public void setInscripcion(boolean inscripcion) {
		this.inscripcion = inscripcion;
	}

	public void setFoto(String nombreArchivo) {
		this.foto = new ImageIcon(nombreArchivo);
	}

	/**************************************************************************/
	/* 								GETTERS 								  */
	/**************************************************************************/

	public int getBoleta() {
		return this.boleta;
	}

	public String getNombreCompleto() {
		return this.nombre + " " + this.primerAp + " " + this.segundoAp;
	}

	public Horario getHorario() {
		return this.horario;
	}

	public boolean getInscripcion() {
		return this.inscripcion;
	}

	public ImageIcon getFoto() {
		return this.foto;
	}
}