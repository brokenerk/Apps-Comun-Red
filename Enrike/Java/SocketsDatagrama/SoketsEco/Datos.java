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
		return Arrays.copyOf(this.b, this.b.length);
	}

	public int getTotal(){
		return this.t;
	}
}