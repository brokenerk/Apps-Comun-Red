import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class DropBox extends JFrame implements ActionListener {
	JButton BtnSubir, BtnActualizar, BtnDescargar;
	JList<String> archivos;
	static DefaultListModel<String> modelo;
	JPanel panelBotones;
	JProgressBar BarraProgreso;
	JScrollPane scroll;
	//File list[];

	public DropBox() {
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		archivos = new JList<>();
        archivos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        modelo = new DefaultListModel<>();

		Cliente.Actualizar();

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
		    
		if(b == BtnSubir) {
			Cliente.EnviarArchivos();
			//Actualizamos el cliente despues de la subida de archivos
			modelo.clear();
			Cliente.Actualizar();
		}
		else if(b == BtnActualizar) {
			modelo.clear();
			Cliente.Actualizar();
		}
		
		else if(b == BtnDescargar) {

		}
	}

	

	public static void main(String s[]) {
		DropBox f = new DropBox();
		f.setTitle("Practica 1: DropBox ");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700,500);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}