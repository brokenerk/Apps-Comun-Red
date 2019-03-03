import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class inscribirV extends JFrame implements ActionListener {
	JPanel panelInfo, panelFoto, panelDatos, panelBuscar, panelMostrar, panelHorario, panelBotones;
	JButton btnBuscar, btnAgregar, btnInscribir, btnEliminar;
	JLabel lfoto, lGrupo, lNombre, lBoleta, nombre, boleta;
	JComboBox comboBox;
	JScrollPane scrollHorario, scrollGrupo;
	ImageIcon foto;
	/* Para guardar el grupo que quieres ver cuando muestres
	   las materias de ese grupo.*/
	String grupo;
	// Guarda la lista de grupos que un estudiante inscribe
	static JList<String> stringGrupo;
	// Guarda la lista de materias de un grupo
	static JList<String> stringMateria;
	static DefaultListModel<String> modelo;

	public inscribirV() {
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
		lfoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("fotos/2014171285.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		panelFoto.add(lfoto);
		
		// -----------------------------------------------------------------------
		// 						PANEL DE DATOS PERSONALES
		// -----------------------------------------------------------------------

		// Agregamos al panel DATOS la información del usuario
		panelDatos = new JPanel(new GridLayout(2, 2));
		panelDatos.setBorder(BorderFactory.createTitledBorder("DATOS PERSONALES"));		
		panelDatos.setPreferredSize(new Dimension(300, 100));

		lBoleta = new JLabel("Boleta: ");
		boleta = new JLabel("2014171285");
		lNombre = new JLabel("Nombre: ");
		nombre = new JLabel("Abigail Nicolas");
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
		comboBox = new JComboBox();
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

	}
	public void actionPerformed(ActionEvent e) {
		
	}

	public static void main(String s[]) {
		inscribirV f = new inscribirV();
		f.setTitle("Inscribir");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 720);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}