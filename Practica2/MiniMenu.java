import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class MiniMenu extends JFrame implements ActionListener {
	JPanel panelBotones;
	JButton BtnInscribir, BtnCal, BtnHorario, BtnCerrarSesion;
	Alumno alumno = null;

	public MiniMenu(Alumno alumno) {
		//Vamos pasando el objeto alumno, esto a modo de una "sesion"
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

	public static void crearLogin() {
		Login f = new Login();
		f.setTitle("Iniciar sesion");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 200);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}

	public static void crearInscribir(Alumno alumno) {
		InscribirV f = new InscribirV(alumno);
		System.out.println("Enviando objeto alumno a inscribir, abriendo inscribir....");
		f.setTitle("Inscribir");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 720);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}

	public static void crearHorario(Alumno alumno) {
		HorarioV f = new HorarioV(alumno);
		System.out.println("Enviando objeto alumno a ver Horario, abriendo Horario....");
		f.setTitle("Horario");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 720);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}

	public static void crearCalificaciones(Alumno alumno) {
		CalificacionesV f = new CalificacionesV(alumno);
		System.out.println("Enviando objeto alumno a ver Horario, abriendo Horario....");
		f.setTitle("Calificaciones");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 520);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		    
		if(b == BtnInscribir) {
			if(alumno.getInscripcion()){
				// Abrir ventana de Ver horario	
				JOptionPane.showMessageDialog(null, "Su inscripcion ha finalizado.", "Inscripcion finalizada", JOptionPane.ERROR_MESSAGE);
			}
			else {
				// Abrir ventana para inscribirse
				crearInscribir(alumno);
				System.out.print("Cerrando MiniMenu....");
				this.setVisible(false);
				System.out.println(" Cerrado.");
				this.dispose();
			}	
		}
		else if(b == BtnHorario) {
			if(alumno.getInscripcion()) {
				// Abrir ventana de Ver horario	
				crearHorario(alumno);
				System.out.print("Cerrando MiniMenu....");
				this.setVisible(false);
				System.out.println(" Cerrado.");
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Debe finalizar su inscripcion para ver su horario.", "Inscripcion no finalizada", JOptionPane.ERROR_MESSAGE);
			}		
		}
		else if(b == BtnCal) {
			if(alumno.getInscripcion()) {
				// Abrir ventana de Ver horario	
				crearCalificaciones(alumno);
				System.out.print("Cerrando MiniMenu....");
				this.setVisible(false);
				System.out.println(" Cerrado.");
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, "Debe finalizar su inscripcion para ver su horario.", "Inscripcion no finalizada", JOptionPane.ERROR_MESSAGE);
			}		
		}
		else if(b == BtnCerrarSesion){
			//Cerrar sesion, abrir login
			crearLogin();
			System.out.print("Cerrando MiniMenu....");
			this.setVisible(false);
			System.out.println(" Cerrado.");
			alumno = null;
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