import java.io.*;
import java.net.*;

public class ClienteO {
	public static void main(String[] args) {
		try {
			int pto = 1234;
			String host = "127.0.0.1";
			Socket cl = new Socket(host, pto);
			System.out.println("Conexion con servidor establecida... recibiendo objeto");
			ObjectOutputStream oos =  new ObjectOutputStream(cl.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());
			Dato d1 = (Dato)ois.readObject();
			System.out.println("Se recibio objeto con la informacion v1 " +  d1.getV1() + "v2 " +  d1.getV2() + "v3 " +  d1.getV3() + "v4 " + d1.getV4());
			Dato d2 = new Dato("cinco", 6, 7.0f, 8);
			System.out.println("Se recibio objeto con la informacion v1 " +  d2.getV1() + "v2 " +  d2.getV2() + "v3 " +  d2.getV3() + "v4 " + d2.getV4());
			oos.writeObject(d2);
			oos.flush();
			ois.close();
			oos.close();
			cl.close();
		} // try
		catch(Exception e) { 
			e.printStackTrace();
		} // catch
	} // main
} // class