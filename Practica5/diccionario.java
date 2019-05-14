 import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import javax.swing.table.*;

public class diccionario extends JFrame implements ActionListener {
	JPanel panelServidor, panelPalabra, panelDefinicion, panelBotones;
	JButton btnServidor, btnBuscar, btnAgregar, btnVer;
	JLabel lServidor, lPalabra, lDefinicion;
	JTextField fPalabra;
	JScrollPane scrollDefinicion, scrollMostrar;
	JComboBox combo;

	public diccionario() {
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		// -----------------------------------------------------------------------
		// 								PANEL SERVIDOR
		// -----------------------------------------------------------------------

		// Agregamos al panel SERVIDOR, sus componentes
		panelServidor = new JPanel(new GridLayout(1, 3));
		panelServidor.setPreferredSize(new Dimension(100, 200));

		lServidor = new JLabel("Servidor");
		// Creacion del JComboBox y añadir los items.
		combo = new JComboBox();
		combo.addItem("Servidor 1");
		combo.addItem("Servidor 2");
		combo.addItem("Servidor 3");

		btnServidor = new JButton("Ok");
		btnServidor.setPreferredSize(new Dimension(100, 35));
		btnServidor.addActionListener(this);
		
		panelServidor.add(lServidor); panelServidor.add(combo);
		panelServidor.add(btnServidor);
		c.add(panelServidor);

		// -----------------------------------------------------------------------
		// 								PANEL PALABRA
		// -----------------------------------------------------------------------

		panelPalabra = new JPanel(new GridLayout(1, 3));
		panelPalabra.setPreferredSize(new Dimension(100, 200));

		// Agregamos al panel PALABRA, sus componentes
		lPalabra = new JLabel("Palabra");
		fPalabra = new JTextField(20);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.setPreferredSize(new Dimension(100, 35));
		btnBuscar.addActionListener(this);

		panelPalabra.add(lPalabra); panelPalabra.add(fPalabra);
		panelPalabra.add(btnBuscar);


		// -----------------------------------------------------------------------
		// 								PANEL PALABRA
		// -----------------------------------------------------------------------



/*
		// Agregamos al panel DATOS la información del usuario
		panelDatos = new JPanel(new GridLayout(2, 2));
		panelDatos.setBorder(BorderFactory.createTitledBorder("DATOS PERSONALES"));		
		panelDatos.setPreferredSize(new Dimension(300, 100));

		lBoleta = new JLabel("Boleta: ");
		boleta = new JLabel("" + alumno.getBoleta());
		lNombre = new JLabel("Nombre: ");
		nombre = new JLabel(alumno.getNombreCompleto());
		panelDatos.add(lBoleta); panelDatos.add(boleta);
		panelDatos.add(lNombre); panelDatos.add(nombre);

		// -----------------------------------------------------------------------
		// 				PANEL QUE INTEGRA FOTO Y DATOS PERSONALES
		// -----------------------------------------------------------------------
		
		panelInfo = new JPanel(new GridLayout(1, 2));
		panelInfo.setPreferredSize(new Dimension(650, 100));
		panelInfo.add(panelFoto); panelInfo.add(panelDatos);

		c.add(panelInfo);
		
		// -----------------------------------------------------------------------
		// 				PANEL PARA MOSTRAR EL GRUPO QUE SE SELECCIONO
		// -----------------------------------------------------------------------
		
		panelMostrar = new JPanel(new GridLayout(1, 3));
		panelMostrar.setBorder(BorderFactory.createTitledBorder("HORARIO DEL GRUPO SELECCIONADO"));		
		panelMostrar.setPreferredSize(new Dimension(650, 200));

		String[] titulos = {"GRUPO", "MATERIA", "PROFESOR", "CALIFICACION"};
		scrollMostrar = new JScrollPane();
		DefaultTableModel modeloMostrar = new DefaultTableModel(null, titulos);
		tablaMostrar = new JTable(modeloMostrar); 
		scrollMostrar.setViewportView(tablaMostrar);
		int i, j;
	

		// *************** HACER QUE SE VEA EL HORARIO ***************

		// Hace que se permita ver el horario de un grupo seleccionado
		Horario h = alumno.getHorario();
		Grupo[] grupos = h.getGrupos();
		Materia[] materias = h.getMaterias();
		String[] profs = h.getProfesores();
		int[] califs = h.getCalifs();
		
		DefaultTableModel modelo = (DefaultTableModel) tablaMostrar.getModel();
		modelo.setRowCount(0);

		for(i = 0; i < materias.length; i++) {
			System.out.println("Despliego materia del grupo: " + grupos[i].getId());
			Grupo g = Cliente.grupos[grupos[i].getId()];
			String nombreGrupo = g.getNombre();
			String[] filaA = {nombreGrupo, materias[i].getNombre(), profs[i], " " +califs[i]};		
			modelo.addRow(filaA);
		}
		System.out.println("Grupo desplegado actualizado.");

		panelMostrar.add(scrollMostrar);
		c.add(panelMostrar);

		// -----------------------------------------------------------------------
		// 					BOTON DE AGREGAR SELECCION AL HORARIO
		// -----------------------------------------------------------------------
		
		btnRegresar = new JButton("Regresar");
		btnRegresar.setPreferredSize(new Dimension(100, 35));
		btnRegresar.addActionListener(this);
		c.add(btnRegresar);
*/
	}
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b == btnBuscar) {
			System.out.println(" Cerrado.");
		}
	}

	// Crear ventana de MiniMenu
	public static void diccionario() {
		diccionario menu = new diccionario();
		menu.setTitle("DICCIONARIO");
		menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menu.setSize(500, 200);
		menu.setVisible(true);
		menu.setLocationRelativeTo(null);
	}
}