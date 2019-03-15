// CLIENTE
import java.net.*;
import java.io.*;
import java.util.*;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class Cliente {
	private static int pto = 4321;
	private static String host = "127.0.0.1";

	public static Publicacion[] actualizar(DefaultTreeModel modelo){
		Publicacion[] publicaciones = null;
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

			//Obtenemos la raiz del JTree
			DefaultMutableTreeNode root = (DefaultMutableTreeNode)modelo.getRoot();
			//Limpiamos el JTree
			root.removeAllChildren();

			//Añadimos las fechas sin repetir al set
			for(int i = 0; i < publicaciones.length; i++)
				fechas.add(publicaciones[i].getFecha());

			//Ordenamos las fechas de forma descendente (posts mas recientes)
			alFechas.addAll(fechas);
    		fechas.clear();
    		Collections.reverse(alFechas);

    		//Ahora vamos agregando cada post en su respectiva fecha
			for(String f : alFechas){
				DefaultMutableTreeNode fechaNode = new DefaultMutableTreeNode(f);
        		root.add(fechaNode);
        		for(int j = 0; j < publicaciones.length; j++){
        			if(f.equals(publicaciones[j].getFecha())){
        				DefaultMutableTreeNode postNode = new DefaultMutableTreeNode(publicaciones[j].getId() + ": " + publicaciones[j].getNombre());
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
    	return publicaciones;
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