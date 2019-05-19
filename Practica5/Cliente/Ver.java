import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class Ver extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	JButton BtnConsultar, BtnRegresar;
	JList<String> archivos;
	DefaultListModel<String> modelo;
	MouseListener mouseListener;
	JPanel panelBotones;
	JScrollPane scroll;

	public Ver() {
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		archivos = new JList<String>();
        archivos.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        /* Doble clic para ver definición si quieres XD
        mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {

		        if (e.getClickCount() == 2) {
		            int index = archivos.locationToIndex(e.getPoint());
		            String nombreSeleccion = modelo.getElementAt(index);

		            //Revisamos que la seleccion sea un directorio
		            if (Cliente.tipoFile[index] == 1) {
			            modelo.clear();
			            Cliente.AbrirCarpeta(index);
			        }
		        }

		    }
		};*/

		//archivos.addMouseListener(mouseListener);

        modelo = new DefaultListModel<>();
		archivos.setModel(modelo);
        scroll = new JScrollPane(archivos);
        scroll.setMinimumSize(new Dimension(100, 200));
		c.add(scroll);

		panelBotones = new JPanel();
		panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));

	
		BtnConsultar = new JButton("Consultar");
		BtnRegresar = new JButton("Regresar");

		panelBotones.add(BtnConsultar);
		panelBotones.add(BtnRegresar);

		c.add(panelBotones);

		BtnRegresar.addActionListener(this);
		BtnConsultar.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b == BtnConsultar) {
			// ALGO
		}
	}

	// Crear ventana de MiniMenu
	public static void main(String s[]) {
		Ver f = new Ver();
		f.setTitle("Ver diccionario");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 600);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}