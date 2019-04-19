import java.net.*;
import java.io.*;
import java.util.*;

public class Manejador extends Thread {
    protected Socket cl;
    protected DataInputStream dis;
    protected DataOutputStream dos;
    protected String fileName;
    protected Mime mime;

    public Manejador(Socket cl) throws Exception {
        this.cl = cl;
        this.dis = new DataInputStream(this.cl.getInputStream());
        this.dos = new DataOutputStream(this.cl.getOutputStream());
        this.mime = new Mime();
    }

    public void run() {
        try {
            String line = dis.readLine();
            //System.out.println(line);
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

                if(line.indexOf("?") == -1) {
                    //Solicita un archivo
                    getArch(line);

                    if(fileName.compareTo("") == 0)
                        SendA("index.html");
                    else
                        SendA(fileName);

                    System.out.println(fileName);
                }
                else if(line.toUpperCase().startsWith("GET")) {
                    //Envia parametros desde un formulario
                    StringTokenizer tokens = new StringTokenizer(line, "?");
                    String req_a = tokens.nextToken();
                    String req = tokens.nextToken();

                    System.out.println("Token1: " + req_a + "\r\n\r\n");
                    System.out.println("Token2: " + req + "\r\n\r\n");

                    String html = "HTTP/1.0 200 Okay\n\n" +
                                  "<html><head><title>SERVIDOR WEB\n" +
                                  "</title></head><body bgcolor='#AACCFF'><center><h1><br>Parametros Obtenidos..</br></h1>\n" +
                                  "<h3><b>" + req + "</b></h3>\n" +
                                  "</center></body></html>";

                    //Devolvemos un HTML con los parametros
                    dos.write(html.getBytes());
                    dos.flush();
                }
                else {
                    String noImplementado = "HTTP/1.0 501 Not Implemented\n\n";
                    dos.write(noImplementado.getBytes());
                    dos.flush();
                }
            }

            dis.close();
            dos.close();
            cl.close();   
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void getArch(String line) {
        int i, f;
        if(line.toUpperCase().startsWith("GET")) {
            i = line.indexOf("/");
            f = line.indexOf(" ", i);
            fileName = line.substring(i + 1, f);
        }
    }

    public void SendA(String arg) {
        try {
            DataInputStream dis2 = new DataInputStream(new FileInputStream(arg));
            int tam = dis2.available();

            int pos = arg.indexOf(".");
            String extension = arg.substring(pos + 1, arg.length());

            String sb = "HTTP/1.0 200 ok\n" +
                        "Server: Axel Server/1.0 \n" +
                        "Date: " + new Date() + " \n" +
                        "Content-Type: " + mime.get(extension) + " \n" + //Distintos tipos MIME para archivos
                        "Content-Length: " + tam + " \n\n";

            dos.write(sb.getBytes());
            dos.flush();

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
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}