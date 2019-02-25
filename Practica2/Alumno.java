public class Alumno{
	private int boleta;
	private String nombre;
	private String primerAp;
	private String segundoAp;
	private Horario horario;
	private boolean inscripcion;
	//private foto;

	public Alumno(int boleta, String nombre, String primerAp, String segundoAp){
		this.boleta = boleta;
		this.nombre = nombre;
		this.primerAp = primerAp;
		this.segundoAp = segundoAp;
	}

	/*SETTERS*/
	public void setHorario(Horario horario){
		this.horario = horario;
	}

	public void setInscripcion(boolean inscripcion){
		this.inscripcion = inscripcion;
	}

	/*GETTERS*/

	public int getBoleta(){
		return boleta;
	}

	public String getNombreCompleto(){
		return nombre + primerAp + segundoAp;
	}

	public Horario getHorario(){
		return horario;
	}

	public boolean getInscripcion(){
		return inscripcion;
	}
}