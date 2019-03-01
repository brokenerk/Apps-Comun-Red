import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import clases.*;

public class MiniMenu extends JFrame implements ActionListener {
	JPanel panelBotones;
	JButton BtnInscribir, BtnCal, BtnHorario, BtnCerrarSesion;
	Alumno alumno;

	public MiniMenu(Alumno alumno) {
		this.alumno = alumno;
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		panelBotones.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));

		BtnInscribir = new JButton("Inscribir");
		BtnCal = new JButton("Ver calificaciones");
		BtnHorario = new JButton("Ver horario");
		BtnCerrarSesion = new JButton("Cerrar sesion");

		panelBotones.add(BtnInscribir);
		panelBotones.add(BtnCal);
		panelBotones.add(BtnHorario);
		panelBotones.add(BtnCerrarSesion);

		c.add(panelBotones);

		BtnInscribir.addActionListener(this);
		BtnCal.addActionListener(this);
		BtnHorario.addActionListener(this);
		BtnCerrarSesion.addActionListener(this);
	}

	public static void crearLogin(){
		Login f = new Login();
		f.setTitle("Iniciar sesion");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 200);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		    
		if(b == BtnInscribir) {
			// Abrir ventana para inscribirse
		}
		else if(b == BtnCal) {
			// Abrir ventana de ver calificaciones 
		}
		else if(b == BtnHorario) {
			// Abrir ventana de Ver horario			
		}
		else if(b == BtnCerrarSesion){
			//Cerrar sesion, abrir login
			crearLogin();
			System.out.print("Cerrando MiniMenu....");
			this.setVisible(false);
			System.out.println(" Cerrado.");
			this.dispose();
		}
	}

	/*public static void main(String s[]) {
		MiniMenu f = new MiniMenu();
		f.setTitle("MENU");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500, 200);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}*/
}