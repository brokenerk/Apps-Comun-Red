import java.io.Serializable;
import java.util.Arrays;

public class Archivo implements Serializable{
	String nombre;
	int parte;
	byte[] b;
	int total;

	public Archivo(String nombre, int parte, byte[] b, int total){
		this.nombre = nombre;
		this.parte = parte;
		this.b = Arrays.copyOf(b, b.length);
		this.total = total;
	}

	public String getNombre(){
		return this.nombre;
	}

	public int getParte(){
		return this.parte;
	}

	public byte[] getFileBytes(){
		return this.b;
	}

	public int getTotal(){
		return this.total;
	}
}