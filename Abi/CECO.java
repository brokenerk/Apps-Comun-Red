// 29 de Enero 2019
// Socket bloqueante, CLIENTE

// ES mejor importar clase por clase
import java.net.*;
import java.io.*;
public class CHM {
	public static void main(String[] args) {
		try {
			// Asocio un flujo de lectura
			// InputStreamreader es como un puente, así puedo leer parrafos 
			// Convierte un flujo orientado a byte a caracter
			System.out.print("\n Escribe la direccion del servidor:");

			BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
			
			String host = br2.readLine(); // Para pruebas se usa "127.0.0.1"
			int pto = 9999; // Se puede usar el rango de 1024-65535
			Socket cl = new Socket(hots, pto);
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(cl.getOutputStream()));
			BufferedReader br = new BufferedReader(new InputStreamReader(cl.getInputStream()));
			
			for(;;) {
				System.out.println("Conexion establecida ... recibiendo datos");
				String msj = br2.readLine();
					// Si es la cadena especial para cerrar el ciclo
					if(msj.compareToIgnoreCase("salir") == 0) {
						System.out.println("Me salgo");
						br2.close();
						pw.close();
						cl.close();
						break;
					}
					else {
						String eco = msj;
						pw.println(eco);
						pw.flush();
						String ms = br.readLine();
						System.out.println(ms);
					}	
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
