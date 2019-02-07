import java.io.Serializable;

public class Dato implements Serializable{
	String v1;
	int v2; //translent
	float v3;
	long v4;

	/*	primitivos = 0
		objetos = null
	*/

	public Dato(String v1, int v2, float v3, long v4){
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
		this.v4 = v4;
	}

	String getV1(){
		return this.v1;
	}

	int getV2(){
		return this.v2;
	}

	float getV3(){
		return this.v3;
	}

	long getV4(){
		return this.v4;
	}
}//class