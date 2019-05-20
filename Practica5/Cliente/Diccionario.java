import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class Diccionario extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	JPanel panelServidor, panelPalabra, panelDefinicion, panelBotones;
	JButton btnServidor, btnBuscar, btnAgregar, btnVer, btnLimpiar;
	JLabel lServidor, lPalabra, lDefinicion;
	public static JTextField tfPalabra;
	JScrollPane scroll;
	JComboBox<String> combo;
	public static JTextArea taDefinicion;

	public Diccionario() {
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		// -----------------------------------------------------------------------
		// 								PANEL SERVIDOR
		// -----------------------------------------------------------------------

		// Agregamos al panel SERVIDOR, sus componentes
		panelServidor = new JPanel(new GridLayout(1, 2));
		panelServidor.setPreferredSize(new Dimension(550, 50));
		panelServidor.setBorder(BorderFactory.createTitledBorder("Elegir"));		

		lServidor = new JLabel("Servidor");

		combo = new JComboBox<String>();


		btnServidor = new JButton("Conectar - Cambiar");
		btnServidor.setPreferredSize(new Dimension(10, 10));
		btnServidor.addActionListener(this);
		
		panelServidor.add(lServidor); panelServidor.add(combo);
		panelServidor.add(btnServidor);
		c.add(panelServidor);

		Cliente.cargarServidores();

		for(int i = 0; i < Cliente.nombresServidores.length; i++){
			combo.addItem("Host: " + Cliente.infoServidor[i][0] + ". Puerto: " + Cliente.infoServidor[i][1]);
		}

		// -----------------------------------------------------------------------
		// 								PANEL PALABRA
		// -----------------------------------------------------------------------

		panelPalabra = new JPanel(new GridLayout(1, 3));
		panelPalabra.setPreferredSize(new Dimension(550, 50));
		panelPalabra.setBorder(BorderFactory.createTitledBorder("Concepto"));		

		// Agregamos al panel PALABRA, sus componentes
		lPalabra = new JLabel("Palabra");
		tfPalabra = new JTextField(20);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setPreferredSize(new Dimension(100, 35));
		btnBuscar.addActionListener(this);
		btnBuscar.setEnabled(false);

		panelPalabra.add(lPalabra); panelPalabra.add(tfPalabra);
		panelPalabra.add(btnBuscar);
		c.add(panelPalabra);

		// -----------------------------------------------------------------------
		// 							PANEL DEFINICION
		// -----------------------------------------------------------------------
		panelDefinicion = new JPanel(new GridLayout(1, 1));
		panelDefinicion.setPreferredSize(new Dimension(550, 400));
		panelDefinicion.setBorder(BorderFactory.createTitledBorder("Definicion"));		

        // Componentes de PANEL definicion
        taDefinicion = new JTextArea(16, 58);
        //taDefinicion.setEditable(false); // set textArea non-editable
        taDefinicion.setLineWrap(true);
		taDefinicion.setWrapStyleWord(true);
        scroll = new JScrollPane(taDefinicion);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        //Add Textarea in to middle panel
        panelDefinicion.add(scroll);

        c.add(panelDefinicion);

        // -----------------------------------------------------------------------
		// 							BOTONES
		// -----------------------------------------------------------------------

        btnAgregar = new JButton("Agregar");
        btnAgregar.setPreferredSize(new Dimension(100, 35));
		btnAgregar.addActionListener(this);
		btnAgregar.setEnabled(false);

        btnVer = new JButton("Ver todo");
        btnVer.setPreferredSize(new Dimension(100, 35));
		btnVer.addActionListener(this);
		btnVer.setEnabled(false);

		btnLimpiar = new JButton("Limpiar");
        btnLimpiar.setPreferredSize(new Dimension(100, 35));
		btnLimpiar.addActionListener(this);

		c.add(btnAgregar);
		c.add(btnVer);
		c.add(btnLimpiar);
	}

	public void abrirVisualizacion(){
		Visualizacion v = new Visualizacion();
		v.setTitle("Visualizacion Diccionario");
		v.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		v.setSize(600, 600);
		v.setVisible(true);
		v.setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b == btnBuscar) {
			// Buscar palabra elegida
			String palabra = tfPalabra.getText();
			String definicion = Cliente.buscarPalabra(palabra);

			if(definicion.equals("No encontrada")){
				JOptionPane.showMessageDialog(null, "La palabra no esta registrada en ninguno de los servidores.", "Definicion no encontrada.", JOptionPane.ERROR_MESSAGE);
			}
			else{
				taDefinicion.setText("");
				taDefinicion.setText(definicion);
			}
		}
		else if(b == btnVer) {
			// Ver todas las palabras
			abrirVisualizacion();
		}
		else if(b == btnAgregar) {
			// Agregar palabra nueva
			String palabra = tfPalabra.getText();
			String definicion = taDefinicion.getText();

			// No existe la palabra
			if(Cliente.agregarPalabra(palabra, definicion))
				JOptionPane.showMessageDialog(null, "Se agrego la palabra " +  palabra + " correctamente");
			else 
				JOptionPane.showMessageDialog(null, "La palabra ya ha sido registrada en algun servidor.", "La palabra ya existe", JOptionPane.ERROR_MESSAGE);
			
			tfPalabra.setText("");
			taDefinicion.setText("");
		}
		else if(b == btnServidor) {
			// Asignar servidor elegido
			btnAgregar.setEnabled(true);
			btnBuscar.setEnabled(true);
			btnVer.setEnabled(true);
			Cliente.asignarServidor(combo.getSelectedIndex());
		}
		else if(b == btnLimpiar) {
			tfPalabra.setText("");
			taDefinicion.setText("");
		}
	}

	// Crear ventana de MiniMenu
	public static void main(String s[]) {
		Diccionario f = new Diccionario();
		f.setTitle("Diccionario Distribuido");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 600);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}