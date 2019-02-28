import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class login extends JFrame implements ActionListener {
	JPanel panelBotones, panelEntrada;
	JTextField [] tj;
    JLabel [] l;
	JButton BtnIniciar;
	String[] label = {"Boleta:", "Contrasena"};
	public login() {
		int i;
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.PAGE_AXIS));

		// Para panel de entradas
		panelEntrada = new JPanel(new GridLayout(2, 2));
		panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 20, 100, 10));
		l = new JLabel[2];
		tj = new JTextField[2];

		for(i = 0; i < 2; i++) {
			l[i] = new JLabel(label[i]);
			tj[i] = new JTextField();
		}

		for(i = 0; i < 2; i++) {
			panelEntrada.add(l[i]);
			panelEntrada.add(tj[i]);
		}
		
		c.add(panelEntrada);

  		// Para panel de botones
		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
		panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 40, 150, 10));
		BtnIniciar = new JButton("Iniciar Sesion");
		
		panelBotones.add(BtnIniciar);
		
		c.add(panelBotones);

		BtnIniciar.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		    
		if(b == BtnIniciar) {
			// Abrir ventana para MiniMenu
		}
	}

	public static void main(String s[]) {
		login f = new login();
		f.setTitle("Iniciar sesion");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 400);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}