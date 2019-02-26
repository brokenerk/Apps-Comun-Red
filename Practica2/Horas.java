import java.io.Serializable;

public class Horas implements Serializable{
	private String[] horarioSemana;

	public Horas(){
		this.horarioSemana = new String[5];
	}

	public void setHoras(String hora, int i){
		this.horarioSemana[i] = hora;
	}

	public String[] getHoras(){
		return this.horarioSemana;
	}
}