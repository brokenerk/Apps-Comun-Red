import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class DropBox extends JFrame implements ActionListener{
	JButton BtnSubir, BtnActualizar;
	JList<String> archivos;
	DefaultListModel<String> modelo;
	JPanel p, panelBotones;
	JProgressBar BarraProgreso;
	File list[];
	static String rutaServer = System.getProperty("user.home") + "\\Desktop\\serverP1\\";

	public DropBox(){
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		p = new JPanel();
		p.setLayout(new GridLayout(5,10));
		//p.setAlignmentX(Component.CENTER_ALIGNMENT);

		archivos = new JList<>();
        archivos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        modelo = new DefaultListModel<>();

		VerArchivos(rutaServer);

		archivos.setModel(modelo);
        p.add(archivos);
        p.add(new JScrollPane(archivos));
		

		//scroll.setViewportView(archivos);
		c.add(p);

		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		//botones=new JButton[40];

		BarraProgreso = new JProgressBar(0, 100);
		BarraProgreso.setAlignmentX(Component.CENTER_ALIGNMENT);
		c.add(BarraProgreso);

		BtnSubir = new JButton("Subir Archivo");
		BtnActualizar = new JButton("Actualizar");

		panelBotones.add(BtnSubir);
		panelBotones.add(BtnActualizar);

		c.add(panelBotones);

		BtnSubir.addActionListener(this);
		BtnActualizar.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e){
		JButton b = (JButton) e.getSource();
		if(b == BtnSubir) Cliente.EnviarArchivo();
		else if(b == BtnActualizar) VerArchivos(rutaServer);
	}

	public void VerArchivos(String path){
		//Leemos la carpeta
		modelo.clear();
		File serverPath = new File(path);

		//Revisamos si existe la carpeta, sino la creamos
		if(!serverPath.exists()){
			serverPath.mkdir();
		}

        list = serverPath.listFiles();

        String info = "";

        for (File f : list){
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

	public static void main(String s[]){
		DropBox f = new DropBox();
		f.setTitle("Practica 1: DropBox " + rutaServer);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000,500);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}

}