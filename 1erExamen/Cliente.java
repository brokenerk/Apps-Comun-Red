// CLIENTE
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.*;

public class Cliente {
	private static int pto = 4321;
	private static String host = "127.0.0.1";
	private static String rutaDirectorios = "";
	public static String sep = System.getProperty("file.separator");

	//Objetos para la pantalla de ForoV
	private static Publicacion[] publicaciones;

	/***************************************************
				ENVIAR IMAGEN
	****************************************************/

	public static void EnviarPublicacionCompleta(String nombrePublicacion, String comentario, File f, String pathOrigen) {
		try {
			Socket cl = new Socket(host, pto);
	        DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //OutputStream

    		String nombre = f.getName();
    		
            long tam = f.length();

            System.out.println("\nSe envia el archivo " + pathOrigen + " con " + tam + " bytes");
            DataInputStream dis = new DataInputStream(new FileInputStream(pathOrigen)); // InputStream

            //La bandera tiene el valor de 3  = Enviar imagen
            dos.writeInt(3); dos.flush();

			//Se envia info de la imagen

            dos.writeUTF(nombre); dos.flush();
            dos.writeLong(tam);	dos.flush();

			dos.writeUTF(nombrePublicacion); dos.flush();
			dos.writeUTF(comentario); dos.flush();

            long enviados = 0;
            int pb = 0;
            int n = 0, porciento = 0;
            byte[] b = new byte[2000];

            while(enviados < tam) {
                n = dis.read(b);
                dos.write(b, 0, n);
                dos.flush();
                enviados += n;
                porciento = (int)((enviados * 100) / tam);
                System.out.println("\r Enviando el " + porciento + "% --- " + enviados + "/" + tam + " bytes");
            } // while

            JOptionPane.showMessageDialog(null, "Se ha subido el archivo " + nombre + " con tamanio: " + tam);
            dis.close(); 
            dos.close(); 
            cl.close();
	    } // Try
	    catch(Exception e) {
            e.printStackTrace();
        }
	} // Class Enviar Imagen


	public static Publicacion descargarComentarios(int IdPublicacion) {
		Publicacion p = null;
		try {
			Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //Enviar datos

			// Bandera con valor 2 = CargarComentarios
			dos.writeInt(2);
			dos.flush();
			System.out.println("Enviando datos: " + IdPublicacion + ", esperando servidor...");

			// Enviar boleta y psswd de los textbox
			dos.writeInt(IdPublicacion);
			dos.flush();

			// Recibir objetos
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); 
			
			// Importante hacer un cast
			p = (Publicacion) ois.readObject();

			dos.close();
			ois.close();
			cl.close();
		}
		catch(Exception e) {
    		e.printStackTrace();
    	} // Catch
    	return p;
	}

	//Sirve para obtener el ID de la publicacion seleccionada en el JTree
	public static int stringToID(String contenido){
		//Contenido --> ID: XXXX - Nombre: blablabla
		String[] eliminarNombre = contenido.split(" - "); // --> ID: XXX
		String[] eliminarResto = eliminarNombre[0].split(": "); //--> XXXX
		return Integer.parseInt(eliminarResto[1]);
	}

	public static void actualizar(DefaultTreeModel modelo){
		publicaciones = null;
		try {
			Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //Enviar datos

			// Bandera con valor 1 = Actualizar
			dos.writeInt(1);
			dos.flush();

			// Recibir objetos
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); 
			
			// Importante hacer un cast
			publicaciones = (Publicacion[]) ois.readObject();
			System.out.println("Publicaciones recibidas del servidor. Actualizando cliente..");

			//Creamos un set para evitar repetir fechas, ordenarlas y clasificar los posts por fecha
			Set<String> fechas = new TreeSet<String>(); 
			ArrayList<String> alFechas = new ArrayList<String>();
			int longitud = publicaciones.length;

			//Obtenemos la raiz del JTree
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)modelo.getRoot();
			//Limpiamos el JTree
			root.removeAllChildren();

			//Añadimos las fechas sin repetir al set
			for(int i = 0; i < longitud; i++)
				fechas.add(publicaciones[i].getFecha());

			//Ordenamos las fechas de forma descendente (posts mas recientes)
			alFechas.addAll(fechas);
    		fechas.clear();
    		Collections.reverse(alFechas);

    		//Ahora vamos agregando cada post en su respectiva fecha
			for(String f : alFechas){
				DefaultMutableTreeNode fechaNode = new DefaultMutableTreeNode(f);
        		root.add(fechaNode);
        		for(int j = 0; j < longitud; j++){
        			if(f.equals(publicaciones[j].getFecha())){
        				DefaultMutableTreeNode postNode = new DefaultMutableTreeNode("ID: " + publicaciones[j].getId() + " - " + publicaciones[j].getNombre());
        				fechaNode.add(postNode);
        			}
        		}
        		//Recargamos el JTree con los nuevos nodos
        		modelo.reload(root);
			}

			alFechas.clear();
			dos.close();
			ois.close();
			cl.close();
		}
		catch(Exception e) {
    		e.printStackTrace();
    	} // Catch
	}

	public static Usuario iniciarSesion(String nickname_tmp, String psswd_tmp) {
		Usuario usuarioActual = null;
		
		try {
			Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //Enviar datos

			// Bandera con valor 0 = Login
			dos.writeInt(0);
			dos.flush();
			System.out.println("Enviando datos: " + nickname_tmp + " - " + psswd_tmp + ", esperando servidor...");

			// Enviar boleta y psswd de los textbox
			dos.writeUTF(nickname_tmp);
			dos.flush();
			dos.writeUTF(psswd_tmp);
			dos.flush();

			// Recibir objetos
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); 
			
			// Importante hacer un cast
			usuarioActual = (Usuario) ois.readObject();
			//System.out.println("Objeto recibido");

			dos.close();
			ois.close();
			cl.close();
		}
		catch(Exception e) {
    		e.printStackTrace();
    	} // Catch
    	return usuarioActual;
	}
}