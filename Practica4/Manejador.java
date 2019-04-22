import java.net.*;
import java.io.*;
import java.util.*;

public class Manejador extends Thread {
    protected Socket cl;
    protected BufferedReader br;
    protected DataOutputStream dos;
    protected Mime mime;

    public Manejador(Socket cl) throws Exception {
        this.cl = cl;
        this.br = new BufferedReader(new InputStreamReader(this.cl.getInputStream()));
        this.dos = new DataOutputStream(this.cl.getOutputStream());
        this.mime = new Mime();
    }

    @Override
    public void run() {
        try {
            String line = br.readLine();
            
            if(line == null)
            {
                String vacia = "<html><head><title>Servidor WEB</title><body bgcolor='#AACCFF'>Linea Vacia</body></html>";
                dos.write(vacia.getBytes());
                dos.flush();
            }
            else{
                System.out.println("\nCliente Conectado desde: " + cl.getInetAddress());
                System.out.println("Por el puerto: " + cl.getPort());
                System.out.println("Datos: " + line + "\r\n\r\n");

                //Metodo GET
                if(line.toUpperCase().startsWith("GET")) {
                	if(line.indexOf("?") == -1) {
	                    //Solicita un archivo
	                    int i = line.indexOf("/");
				        int f = line.indexOf(" ", i);
				        String fileName = line.substring(i + 1, f);

	                    if(fileName.compareTo("") == 0)
	                        fileName = "index.html";

	                    enviarRecurso(fileName);
	                    System.out.println(fileName);
	                }
	                else{
	                    //Envia parametros desde un formulario
	                    StringTokenizer tokens = new StringTokenizer(line, "?");
	                    String req_a = tokens.nextToken();
	                    String req = tokens.nextToken();

	                    System.out.println("Token1: " + req_a + "\r\n\r\n");
	                    System.out.println("Token2: " + req + "\r\n\r\n");

	                    //Respuesta GET, devolvemos un HTML con los parametros del formulario
	                    String html = "HTTP/1.1 200 OK\n" +
	                    			  "Date: " + new Date() + " \n" +
		              				  "Server: Axel Server/1.0 \n" +
		              				  "Content-Type: text/html \n\n" +

									  "<html><head><title>Metodo GET\n" +
	                                  "</title></head><body bgcolor='#AACCFF'><center><h1><br>Parametros obtenidos por medio de GET.</br></h1>\n" +
	                                  "<h3><b>" + req + "</b></h3>\n" +
	                                  "</center></body></html>";

	                    dos.write(html.getBytes());
	                    dos.flush();
	                    System.out.println("Respuesta: \n" + html);
	                }
                }
                else {
                	//Metodos no implementados en el servidor
                    String error501 = "HTTP/1.1 501 Not Implemented\n" +
                    				  "Date: " + new Date() + " \n" +
		              				  "Server: Axel Server/1.0 \n" +
		              				  "Content-Type: text/html \n\n" +

	        						  "<html><head><meta charset='UTF-8'><title>Error 501</title></head>" +
	        						  "<body><h1>Error 501: No implementado.</h1>" +
	        						  "<p>El método HTTP o funcionalidad solicitada no está implementada en el servidor.</p>" +
	        						  "</body></html>";
                    dos.write(error501.getBytes());
                    dos.flush();
                    System.out.println("Respuesta: \n" + error501);
                }
            }
            br.close();
            dos.close();
            cl.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void enviarRecurso(String arg) {
        try {
        	File f = new File(arg);
        	String sb = "HTTP/1.1 200 OK\n";

        	if(!f.exists()){
        		arg = "404.html"; //Recurso no encontrado
        		sb = "HTTP/1.1 404 Not Found\n";
        	}
        	else if(f.isDirectory()){
        		arg = "403.html"; //Recurso privado
        		sb = "HTTP/1.1 403 Forbidden\n";
        	}

    		DataInputStream dis2 = new DataInputStream(new FileInputStream(arg)); 
    		int tam = dis2.available();

            int pos = arg.indexOf(".");
            String extension = arg.substring(pos + 1, arg.length());

            //Enviamos las cabeceras de la respuesta HTTP
            sb = sb + "Date: " + new Date() + " \n" +
		              "Server: Axel Server/1.0 \n" +
		              //Distintos tipos MIME para distintos tipos de archivos
		              "Content-Type: " + mime.get(extension) + " \n" + 
		              "Content-Length: " + tam + " \n\n";

            dos.write(sb.getBytes());
            dos.flush();

            System.out.println("Respuesta: \n" + sb);

            //Respuesta GET, enviamos el archivo solicitado
            byte[] b = new byte[1024];
            long enviados = 0;
            int n = 0;
            
            while(enviados < tam) {
                n = dis2.read(b);
                dos.write(b, 0, n);
                dos.flush();
                enviados += n;
            }
            dis2.close(); 	
            
        }
        catch(Exception e) {
        	String error = e.getMessage();
            System.out.println(error);
            //e.printStackTrace();
        }
    }
}