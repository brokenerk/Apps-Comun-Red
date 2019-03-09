import java.io.Serializable;
import java.util.Arrays;

public class Datos implements Serializable{
	//String archivo;
	int n;
	byte[] b;
	int t;

	public Datos(int n, byte[] b, int t){
		this.n = n;
		this.b = Arrays.copyOf(b, b.length); //Arrays.copy()
		this.t = t;
	}
}