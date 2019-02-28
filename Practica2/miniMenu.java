import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class miniMenu extends JFrame implements ActionListener {
	JPanel panelBotones;
	JButton BtnInscribir, BtnCal, BtnHorario;

	public miniMenu() {
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));

		BtnInscribir = new JButton("Inscribir");
		BtnCal = new JButton("Ver calificaciones");
		BtnHorario = new JButton("Ver horario");

		panelBotones.add(BtnInscribir);
		panelBotones.add(BtnCal);
		panelBotones.add(BtnHorario);

		c.add(panelBotones);

		BtnInscribir.addActionListener(this);
		BtnCal.addActionListener(this);
		BtnHorario.addActionListener(this);
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
	}

	public static void main(String s[]) {
		miniMenu f = new miniMenu();
		f.setTitle("MENU");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500, 200);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}