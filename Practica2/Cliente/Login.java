import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class Login extends JFrame implements ActionListener {
	JPanel panelBotones, panelEntrada;
	JTextField  TxtBoleta;
	JPasswordField TxtPassword;
    JLabel [] l;
	JButton BtnIniciar;
	String[] label = {"Boleta:", "Contrasena:"};

	public Login() {
		int i;
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		// Para panel de entradas
		panelEntrada = new JPanel();
		panelEntrada.setLayout(new BoxLayout(panelEntrada, BoxLayout.Y_AXIS));
		panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		l = new JLabel[2];

		for(i = 0; i < 2; i++) {
			l[i] = new JLabel(label[i]);
		}

		TxtBoleta = new JTextField();
		TxtPassword = new JPasswordField();
		TxtBoleta.setAlignmentX(Component.CENTER_ALIGNMENT);
		TxtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);


		panelEntrada.add(l[0]);
		panelEntrada.add(TxtBoleta);
		panelEntrada.add(l[1]);
		panelEntrada.add(TxtPassword);
		
		c.add(panelEntrada);

  		// Para panel de botones
		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
		panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 45, 1000, 10));
		BtnIniciar = new JButton("Iniciar Sesion");
		BtnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		panelBotones.add(BtnIniciar);
		
		c.add(panelBotones);

		BtnIniciar.addActionListener(this);
		
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		    
		if(b == BtnIniciar) {
			// Iniciar sesion
			if(TxtBoleta.getText().length() == 0 || TxtPassword.getPassword().length == 0) {
				JOptionPane.showMessageDialog(null, "Ingrese sus datos.");
			}
			else {
				char[] pass = TxtPassword.getPassword();
				String passwd_temp = String.valueOf(pass);
				int boleta_temp = 0;

				try {
					boleta_temp = Integer.parseInt(TxtBoleta.getText());
					Alumno alumno = Cliente.iniciarSesion(boleta_temp, passwd_temp);

					if(alumno != null){
						System.out.println("Objeto alumno recibido con boleta: " + alumno.getBoleta());

						/*Construir objeto MiniMenu y abrirlo*/
						crearMiniMenu(alumno);
						/*Cerrar login*/
						System.out.print("Cerrando Login....");
						this.setVisible(false);
						System.out.println(" Cerrado.");
						this.dispose();
					}
					else {
						System.out.println("Objeto recibido es null");
						JOptionPane.showMessageDialog(null, "Contrasena y/o boleta incorrectas. Intente de nuevo.", "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
						TxtBoleta.setText("");
						TxtPassword.setText("");
					}
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(null, "El campo de boleta solo admite numeros.", "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
					TxtBoleta.setText("");
					TxtPassword.setText("");
				}
			}
		}
	}

	public static void crearMiniMenu(Alumno alumno) {
		MiniMenu menu = new MiniMenu(alumno);
		System.out.println("Enviando objeto alumno a MiniMenu, abriendo MiniMenu....");
		menu.setTitle("MENU");
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(500, 200);
		menu.setVisible(true);
		menu.setLocationRelativeTo(null);
	}

	public static void main(String s[]) {
		Login f = new Login();
		f.setTitle("Iniciar sesion");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 200);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}