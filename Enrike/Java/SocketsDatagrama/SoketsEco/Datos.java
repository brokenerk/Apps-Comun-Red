import java.io.Serializable;
import java.util.Arrays;

public class Datos implements Serializable{
	//String archivo;
	int n;
	byte[] b;
	int t;

	public Datos(int n, byte[] b, int len, int t){
		this.n = n;
		this.b = Arrays.copyOf(b, len); //Arrays.copy()
		this.t = t;
	}

	public int getParte(){
		return this.n;
	}

	public byte[] getContenido(){
		return this.b;
	}

	public int getTotal(){
		return this.t;
	}
}