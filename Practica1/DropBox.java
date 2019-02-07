import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class DropBox extends JFrame implements ActionListener {
	JButton BtnSubir, BtnActualizar, BtnDescargar;
	JList<String> archivos;
	DefaultListModel<String> modelo;
	JPanel panelBotones;
	public JProgressBar BarraProgreso;
	JScrollPane scroll;
	File list[];

	static String rutaServer = System.getProperty("user.home") + "\\Desktop\\serverP1\\";

	public DropBox() {
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		archivos = new JList<>();
        archivos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        modelo = new DefaultListModel<>();

		VerArchivos(rutaServer);

		archivos.setModel(modelo);
		
        scroll = new JScrollPane(archivos);
        scroll.setMinimumSize(new Dimension(100, 200));

		c.add(scroll);

		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));

		BarraProgreso = new JProgressBar(0, 100);
		BarraProgreso.setAlignmentX(Component.CENTER_ALIGNMENT);
		c.add(BarraProgreso);

		BtnSubir = new JButton("Subir Archivo");
		BtnActualizar = new JButton("Actualizar");
		BtnDescargar = new JButton("Descargar");

		panelBotones.add(BtnSubir);
		panelBotones.add(BtnActualizar);
		panelBotones.add(BtnDescargar);

		c.add(panelBotones);

		BtnSubir.addActionListener(this);
		BtnActualizar.addActionListener(this);
		BtnDescargar.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		    
		if(b == BtnSubir) Cliente.EnviarArchivo();
		else if(b == BtnActualizar) VerArchivos(rutaServer);
		
		else if(b == BtnDescargar) {
			System.out.println("DESCARGA");
 			// Get the selected values.
			// Object[] selections = archivos.getSelectedValues();
     		for (Object value : archivos.getSelectedValues()) {
		        System.out.println("DESCARGA: " + value.toString());
		        // Enviar el nombre del archivo al servidor, para
		        // que el servidor me mande el archivo que yo quiero
		        // descargar.
		    }
		}
	}

	public void VerArchivos(String path) {
		// Leemos la carpeta
		modelo.clear();
		File serverPath = new File(path);

		//Revisamos si existe la carpeta, sino la creamos
		if(!serverPath.exists()) {
			serverPath.mkdir();
		}

        list = serverPath.listFiles();
        
        String info = "";
        for (File f : list) {
            if (f.isDirectory()) { 
                
                //walk( f.getAbsolutePath() ); 
                info = "" + f.getAbsoluteFile();
                System.out.println("Dir: " + f.getAbsoluteFile()); 
            } 
            else { 
            	info = f.getName() + " ---- " + f.length();
                System.out.println("File: " + f.getAbsoluteFile()); 
            } 
            modelo.addElement(info);
        } 
	}

	public static void main(String s[]) {
		DropBox f = new DropBox();
		f.setTitle("Practica 1: DropBox " + rutaServer);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700,500);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}