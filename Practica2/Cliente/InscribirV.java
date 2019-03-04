import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import javax.swing.table.*;

public class InscribirV extends JFrame implements ActionListener {
	JPanel panelInfo, panelFoto, panelDatos, panelBuscar, panelMostrar, panelHorario, panelBotones;
	JButton btnBuscar, btnAgregar, btnInscribir, btnEliminar;
	JLabel lfoto, lGrupo, lNombre, lBoleta, nombre, boleta;
	JComboBox<String> comboBox;
	JScrollPane scrollMostrar, scrollHorario;
	ImageIcon foto;

	// Guarda la lista de grupos que un estudiante inscribe
	static JList<String> stringGrupo;
	// Guarda la lista de materias de un grupo
	static JList<String> stringMateria;
	static DefaultListModel<String> modelo;
	JTable tablaMostrar, tablaHorario;

	//Modelos para agregar filas a las tablas
	DefaultTableModel modeloMostrar;
	DefaultTableModel modeloHorario;

	Alumno alumno = null;

	public InscribirV(Alumno alumno) {
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
		//lfoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("fotos/2014171285.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
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
		// 						PANEL PARA BUSCAR GRUPO
		// -----------------------------------------------------------------------
		
		// Agregamos al panel GRUPO el comboBox para elegir un grupo
		panelBuscar = new JPanel(new GridLayout(1, 3));
		panelBuscar.setBorder(BorderFactory.createTitledBorder("SELECCIONA UN GRUPO"));		
		panelBuscar.setPreferredSize(new Dimension(650, 50));

		lGrupo = new JLabel("GRUPO: ");
		comboBox = new JComboBox<>();
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		panelBuscar.add(lGrupo); panelBuscar.add(comboBox); panelBuscar.add(btnBuscar);
		
		c.add(panelBuscar);

		// -----------------------------------------------------------------------
		// 				PANEL PARA MOSTRAR EL GRUPO QUE SE SELECCIONO
		// -----------------------------------------------------------------------
		
		panelMostrar = new JPanel(new GridLayout(1, 3));
		panelMostrar.setBorder(BorderFactory.createTitledBorder("HORARIO DEL GRUPO SELECCIONADO"));		
		panelMostrar.setPreferredSize(new Dimension(650, 200));

		String[] titulos = {"GRUPO", "MATERIA", "PROFESOR", "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES"};
		scrollMostrar = new JScrollPane();
		DefaultTableModel modeloMostrar = new DefaultTableModel(null, titulos);
		tablaMostrar = new JTable(modeloMostrar); 
		scrollMostrar.setViewportView(tablaMostrar);
		Object[] fila = new Object[8];
		int i, j;
		/*for(i = 0; i < 10; i++) {
			for(j = 0; j < 8; j++) {
				fila[j] = " ";
			}
			modeloMostrar.addRow(fila);
		}*/
		panelMostrar.add(scrollMostrar);
		c.add(panelMostrar);

		// -----------------------------------------------------------------------
		// 					BOTON DE AGREGAR SELECCION AL HORARIO
		// -----------------------------------------------------------------------
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.setPreferredSize(new Dimension(100, 35));
		btnAgregar.addActionListener(this);
		c.add(btnAgregar);
		
		// -----------------------------------------------------------------------
		// 		PANEL PARA MOSTRAR EL HORARIO ELEGIDO HASTA CIERTO MOMENTO
		// -----------------------------------------------------------------------
		
		panelHorario = new JPanel(new GridLayout(1, 3));
		panelHorario.setBorder(BorderFactory.createTitledBorder("HORARIO SELECCIONADO"));		
		panelHorario.setPreferredSize(new Dimension(650, 200));
		
		scrollHorario = new JScrollPane();
		modeloHorario = new DefaultTableModel(null, titulos);
		tablaHorario = new JTable(modeloHorario); 
		scrollHorario.setViewportView(tablaHorario);
		fila = new Object[8];
		/*for(i = 0; i < 1; i++) {
			for(j = 0; j < 8; j++) {
				fila[j] = " ";
			}
			modeloHorario.addRow(fila);
		}*/
		panelHorario.add(scrollHorario);
		c.add(panelHorario);
		c.add(panelHorario);

		// -----------------------------------------------------------------------
		// 					PANEL PARA BOTON DE INSCRIBIR Y ELIMINAR
		// -----------------------------------------------------------------------
		panelBotones = new JPanel(new GridLayout(1, 2));
		panelBotones.setPreferredSize(new Dimension(650, 40));

		btnInscribir = new JButton("Inscribir");
		btnInscribir.setPreferredSize(new Dimension(100, 35));
		btnInscribir.addActionListener(this);
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setPreferredSize(new Dimension(100, 35));
		btnEliminar.addActionListener(this);
		
		panelBotones.add(btnInscribir); panelBotones.add(btnEliminar);
		c.add(panelBotones);

		// -----------------------------------------------------------------------
		// 					DESCARGAR GRUPOS DESDE EL SERVIDOR
		// -----------------------------------------------------------------------
		Cliente.obtenerGrupos();

		for(j = 0; j < Cliente.grupos.length; j++){
			String grupo = Cliente.grupos[j].getNombre();
			comboBox.addItem(grupo);
		}

	}

	public void crearLogin(){
		Login f = new Login();
		f.setTitle("Iniciar sesion");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 200);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();

		if(b == btnBuscar) {
			// Hace que se permita ver el horario de un grupo seleccionado
			int idGrupo = comboBox.getSelectedIndex();
			Grupo g = Cliente.grupos[idGrupo];
			String nombre = g.getNombre();
			Materia[] materias = g.getMaterias();
			String[] profs = g.getProfesores();
			String[][] horas = g.getHoras();

			DefaultTableModel modelo = (DefaultTableModel) tablaMostrar.getModel();
			modelo.setRowCount(0);

			for(int i = 0; i < materias.length; i++){
				String[] fila = {nombre, materias[i].getNombre(), profs[i], horas[i][0], horas[i][1], horas[i][2], horas[i][3], horas[i][4]};
				modelo.addRow(fila);
			}
			System.out.println("Grupo desplegado actualizado.");
		}
		if(b == btnAgregar) {
			// Se agregan al horario del usuario las materias seleccionado
			DefaultTableModel modelo1 = (DefaultTableModel) tablaMostrar.getModel();
			int[] seleccion = tablaMostrar.getSelectedRows();
			String[] fila = new String[8]; 
			DefaultTableModel modelo2 = (DefaultTableModel) tablaHorario.getModel();

			for(int i = 0; i < seleccion.length; i++){
				for(int j = 0; j < 8; j++){
					fila[j] = (String) modelo1.getValueAt(seleccion[i], j);
				}
				modelo2.addRow(fila);
			}
			System.out.println("Materias agregadas.");

		}
		if(b == btnInscribir) {
			int resp = JOptionPane.showConfirmDialog(null, "Esta seguro de inscribir el siguiente horario?", "Confirmacion inscripcion", JOptionPane.YES_NO_OPTION);

			if(resp == JOptionPane.YES_OPTION){
				// Se inscriben al horario del usuario las materias seleccinadas
				DefaultTableModel modelo2 = (DefaultTableModel) tablaHorario.getModel();
				int numMaterias = tablaHorario.getRowCount();
				String[] grupos = new String[numMaterias];
				String[] materias = new String[numMaterias];

				for(int i = 0; i < numMaterias; i++){
					grupos[i] = (String) modelo2.getValueAt(i, 0);
					materias[i] = (String) modelo2.getValueAt(i, 1);
				}

				//Enviamos el horario al servidor para que este inscriba al alumno dentro de su catalogo
				System.out.println("Enviando horario....");
				Cliente.enviarHorario(grupos, materias, alumno.getBoleta());
				System.out.println("Inscripcion correcta");
				JOptionPane.showMessageDialog(null, "Inscripcion correcta. Debe iniciar nuevamente sesion para ver su horario y calificaciones.");
				crearLogin();
				System.out.print("Cerrando InscribirV....");
				this.setVisible(false);
				System.out.println(" Cerrado.");
				alumno = null;
				this.dispose();
			}
		}
		if(b == btnEliminar) {
			// Se eliman del horario del usuario las masterias seleccionadas
			DefaultTableModel modelo2 = (DefaultTableModel) tablaHorario.getModel();
			int[] seleccion = tablaHorario.getSelectedRows();

			for(int i = seleccion.length - 1; i >= 0; i--){
				modelo2.removeRow(seleccion[i]);
			}
			System.out.println("Materias eliminadas.");
		}
	}
}