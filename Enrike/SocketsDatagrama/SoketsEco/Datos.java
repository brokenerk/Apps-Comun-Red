import java.io.Serializable;

public class Datos implements Serializable{
	//String archivo;
	int n;
	byte[] b;
	int t;

	public Datos(int n, byte[] b, int t){
		this.n = n;
		this.b = b; //Arrays.copy()
		this.t = t;
	}
}