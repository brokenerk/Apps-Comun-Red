import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class Visualizacion extends JFrame implements ActionListener {
	private static final long serialVisualizacionsionUID = 1L;
	
	JButton BtnConsultar, BtnRegresar;
	JList<String> palabras;
	public static DefaultListModel<String> modelo;
	MouseListener mouseListener;
	JPanel panelBotones;
	JScrollPane scroll;

	public Visualizacion() {
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		palabras = new JList<String>();
        palabras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Doble clic para ver definición si quieres XD
        mouseListener = new MouseAdapter() {
		    public void mouseClicked(MouseEvent e) {
		        if (e.getClickCount() == 2) {
		        	Cliente.consultarDesdeVisualizacion(palabras.getSelectedValue());
		        	cerrarVisualizacion();
		        }
		    }
		};

		palabras.addMouseListener(mouseListener);

        modelo = new DefaultListModel<>();
		palabras.setModel(modelo);
        scroll = new JScrollPane(palabras);
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

		//Visualizar palabras del diccionario
		modelo.clear();
		Cliente.visualizarDiccionario();
	}

	public void cerrarVisualizacion(){
		System.out.print("Cerrando Visualizacion....");
		this.setVisible(false);
		System.out.println(" Cerrado.");
		this.dispose();
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b == BtnConsultar) {
			Cliente.consultarDesdeVisualizacion(palabras.getSelectedValue());
			cerrarVisualizacion();
		}
		else if(b == BtnRegresar){
			cerrarVisualizacion();
		}
	}
}