 import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import javax.swing.table.*;

public class Diccionario extends JFrame implements ActionListener {
	JPanel panelServidor, panelPalabra, panelDefinicion, panelBotones;
	JButton btnServidor, btnBuscar, btnAgregar, btnVer;
	JLabel lServidor, lPalabra, lDefinicion;
	JTextField fPalabra;
	JScrollPane scroll;
	JComboBox<String> combo;
	JTextArea mostrarDef;

	public Diccionario() {
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		// -----------------------------------------------------------------------
		// 								PANEL SERVIDOR
		// -----------------------------------------------------------------------

		// Agregamos al panel SERVIDOR, sus componentes
		panelServidor = new JPanel(new GridLayout(1, 3));
		panelServidor.setPreferredSize(new Dimension(550, 50));
		panelServidor.setBorder(BorderFactory.createTitledBorder("Elegir :"));		

		lServidor = new JLabel("Servidor");
		combo = new JComboBox<String>();
		combo.addItem("1");
		combo.addItem("2");
		combo.addItem("3");

		btnServidor = new JButton("Ok");
		btnServidor.setPreferredSize(new Dimension(35, 35));
		btnServidor.addActionListener(this);
		
		panelServidor.add(lServidor); panelServidor.add(combo);
		panelServidor.add(btnServidor);
		c.add(panelServidor);

		// -----------------------------------------------------------------------
		// 								PANEL PALABRA
		// -----------------------------------------------------------------------

		panelPalabra = new JPanel(new GridLayout(1, 3));
		panelPalabra.setPreferredSize(new Dimension(550, 50));
		panelPalabra.setBorder(BorderFactory.createTitledBorder("Elegir "));		

		// Agregamos al panel PALABRA, sus componentes
		lPalabra = new JLabel("Palabra");
		fPalabra = new JTextField(20);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setPreferredSize(new Dimension(100, 35));
		btnBuscar.addActionListener(this);

		panelPalabra.add(lPalabra); panelPalabra.add(fPalabra);
		panelPalabra.add(btnBuscar);
		c.add(panelPalabra);

		// -----------------------------------------------------------------------
		// 							PANEL DEFINICION
		// -----------------------------------------------------------------------
		panelDefinicion = new JPanel(new GridLayout(1, 1));
		panelDefinicion.setPreferredSize(new Dimension(550, 400));
		panelDefinicion.setBorder(BorderFactory.createTitledBorder("Definicion"));		

        // Componentes de PANEL definicion
        mostrarDef = new JTextArea(16, 58);
        mostrarDef.setEditable(false); // set textArea non-editable
        scroll = new JScrollPane(mostrarDef);
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

        btnVer = new JButton("Ver todo");
        btnVer.setPreferredSize(new Dimension(100, 35));
		btnVer.addActionListener(this);

		c.add(btnAgregar);
		c.add(btnVer);
	}
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b == btnBuscar) {
			// Buscar palabra elegida
		}
		else if(b == btnVer) {
			// Ver todas las palabras
		}
		else if(b == btnAgregar) {
			// Agregar palabra nueva
		}
		else if(b == btnServidor) {
			// Asignar servidor elegido
		}
	}

	// Crear ventana de MiniMenu
	public static void main(String s[]) {
		Diccionario f = new Diccionario();
		f.setTitle("DICCIONARIO");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(600, 600);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}