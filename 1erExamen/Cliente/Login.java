import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class Login extends JFrame implements ActionListener {
	JPanel panelBotones, panelEntrada;
	JTextField  TxtNickname;
	JPasswordField TxtPassword;
    JLabel [] l;
	JButton BtnIniciar;
	String[] label = {"Nickname:", "Contrasena:"};

	public Login() {
		int i;
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		// Para panel de entradas
		panelEntrada = new JPanel();
		panelEntrada.setLayout(new BoxLayout(panelEntrada, BoxLayout.Y_AXIS));
		panelEntrada.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
		l = new JLabel[2];

		for(i = 0; i < 2; i++)
			l[i] = new JLabel(label[i]);

		TxtNickname = new JTextField();
		TxtPassword = new JPasswordField();
		TxtNickname.setAlignmentX(Component.CENTER_ALIGNMENT);
		TxtPassword.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelEntrada.add(l[0]);
		panelEntrada.add(TxtNickname);
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

	/*********************************************************************************************
									ACTIONPERFORMED
	*********************************************************************************************/
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b == BtnIniciar) {
			// Iniciar sesion
			if(TxtNickname.getText().length() == 0 || TxtPassword.getPassword().length == 0) {
				JOptionPane.showMessageDialog(null, "Ingrese sus datos.");
			}
			else {
				char[] pass = TxtPassword.getPassword();
				String passwd_tmp = String.valueOf(pass);

				try {
					/*Logica de negocios xd*/
					String nickname_tmp = TxtNickname.getText();
					Usuario usuario = Cliente.iniciarSesion(nickname_tmp, passwd_tmp);

					if(usuario != null){
						System.out.println("Objeto usuario recibido con Id: " + usuario.getId());

						/*Construir objeto Foro y abrirlo*/
						crearForoV(usuario);
						/*Cerrar login*/
						System.out.print("Cerrando Login....");
						this.setVisible(false);
						System.out.println(" Cerrado.");
						this.dispose();
					}
					else {
						System.out.println("Objeto recibido es null");
						JOptionPane.showMessageDialog(null, "Contrasena y/o nickname incorrectos. Intente de nuevo.", "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
						TxtNickname.setText("");
						TxtPassword.setText("");
					}
				}
				catch(Exception ex) {
					ex.printStackTrace();
					TxtNickname.setText("");
					TxtPassword.setText("");
				}
			}
		}
	}

	/*********************************************************************************************
									CREARFORO
	*********************************************************************************************/
	public static void crearForoV(Usuario usuario) {
		System.out.println("Enviando objeto usuario a Foro, abriendo Foro....");
		ForoV f = new ForoV(usuario);
		f.setTitle("Foro");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 720);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
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