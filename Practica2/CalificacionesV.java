import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import javax.swing.table.*;
public class CalificacionesV extends JFrame implements ActionListener {
	JPanel panelInfo, panelFoto, panelDatos, panelHorario, panelMostrar;
	JButton btnRegresar;
	JLabel lfoto, lGrupo, lNombre, lBoleta, nombre, boleta;
	JScrollPane scrollHorario, scrollMostrar;
	ImageIcon foto;

	// Guarda la lista de grupos que un estudiante inscribe
	static JList<String> stringGrupo;
	// Guarda la lista de materias de un grupo
	static JList<String> stringMateria;
	static DefaultListModel<String> modelo;
	JTable tablaMostrar, tablaHorario;

	// Modelos para agregar filas a las tablas
	DefaultTableModel modeloMostrar;
	DefaultTableModel modeloHorario;

	Alumno alumno = null;

	public CalificacionesV(Alumno alumno) {
		//Vamos pasando el objeto alumno, esto a modo de una "sesion"
		this.alumno = alumno;

		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		// -----------------------------------------------------------------------
		// 						PANEL DE FOTO
		// -----------------------------------------------------------------------

		// Agregamos al panel DATOS la información del usuario
		panelFoto = new JPanel(new GridLayout(1, 1));
		panelFoto.setPreferredSize(new Dimension(100, 100));

		// Agregamos al panel FOTO la imagen default
		lfoto = new JLabel(foto);
		lfoto.setIcon(new ImageIcon(alumno.getFoto().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		panelFoto.add(lfoto);
		
		// -----------------------------------------------------------------------
		// 						PANEL DE DATOS PERSONALES
		// -----------------------------------------------------------------------

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

	}
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();

		if(b == btnRegresar) {
			/*Construir objeto MiniMenu y abrirlo*/
			crearMiniMenu(alumno);
			/*Cerrar login*/
			System.out.print("Cerrando VerHorario....");
			this.setVisible(false);
			System.out.println(" Cerrado.");
			this.dispose();
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
}