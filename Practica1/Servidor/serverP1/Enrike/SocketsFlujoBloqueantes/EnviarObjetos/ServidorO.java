import java.io.*;
import java.net.*;

public class ServidorO{
	public static void main(String[] args){
		

		try{
			ServerSocket s = new ServerSocket(1234);
			s.setReuseAddress(true);

			System.out.println("Servidor de objetos iniciado, esperando cliente...");

			for( ; ; )
			{
				Socket cl = s.accept();
				System.out.println("\n\nCliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());
			
				ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
				ObjectInputStream ois = new ObjectInputStream(cl.getInputStream());

				Dato d1 = new Dato("uno", 2, 3.0f, 4);
				System.out.println("Enviando objeto con la informacion v1: " + d1.getV1() +
				" v2: " + d1.getV2() + " v3: " + d1.getV3() + " v4: " + d1.getV4());

				oos.writeObject(d1);
				oos.flush();

				Dato d2 = (Dato) ois.readObject();
				System.out.println("Objeto recibido con la informacion v1: " + d2.getV1() +
				" v2: " + d2.getV2() + " v3: " + d2.getV3() + " v4: " + d2.getV4());

				ois.close();
				oos.close();
				cl.close();
			}//for
			
		}catch(Exception e){
			e.printStackTrace();
		}//catch
	}//main
}
