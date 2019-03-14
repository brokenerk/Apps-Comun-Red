import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import javax.swing.table.*;

public class inicioV extends JFrame implements ActionListener {
	JPanel panelInfo, panelFoto, panelDatos, panelBuscar, panelMostrar,  panelBotones;
	JButton btnBuscar, btnAgregar, btnActualizar, btnSalir;
	JLabel lfoto, lGrupo, lNombre, lBoleta, nombre, boleta;
	JTextField tfBuscar;
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

	public inicioV() {
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
		//lfoto.setIcon(new ImageIcon(alumno.getFoto().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		lfoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("avatars/1.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		panelFoto.add(lfoto);
		
		// -----------------------------------------------------------------------
		// 						PANEL DE DATOS PERSONALES
		// -----------------------------------------------------------------------

		// Agregamos al panel DATOS la información del usuario
		panelDatos = new JPanel(new GridLayout(1, 1));
		panelDatos.setBorder(BorderFactory.createTitledBorder("DATOS PERSONALES"));		
		panelDatos.setPreferredSize(new Dimension(300, 100));

		lNombre = new JLabel("USUARIO: ");
		//nombre = new JLabel(alumno.getNombreCompleto());
		nombre = new JLabel("ENRIKE");
		panelDatos.add(lNombre); panelDatos.add(nombre);

		// -----------------------------------------------------------------------
		// 				PANEL QUE INTEGRA FOTO Y DATOS PERSONALES
		// -----------------------------------------------------------------------
		
		panelInfo = new JPanel(new GridLayout(1, 2));
		panelInfo.setPreferredSize(new Dimension(650, 100));
		panelInfo.add(panelFoto); panelInfo.add(panelDatos);
		c.add(panelInfo);

		// -----------------------------------------------------------------------
		// 						PANEL PARA BUSCAR 
		// -----------------------------------------------------------------------
		
		// Agregamos al panel GRUPO el comboBox para elegir un grupo
		panelBuscar = new JPanel(new GridLayout(1,2));
		panelBuscar.setBorder(BorderFactory.createTitledBorder("BUSCAR"));		
		panelBuscar.setPreferredSize(new Dimension(650, 50));

		tfBuscar = new JTextField();
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		panelBuscar.add(tfBuscar); panelBuscar.add(btnBuscar);
		
		c.add(panelBuscar);

		// -----------------------------------------------------------------------
		// 				PANEL PARA MOSTRAR LOS FOROS
		// -----------------------------------------------------------------------
		
		panelMostrar = new JPanel(new GridLayout(1, 3));
		panelMostrar.setBorder(BorderFactory.createTitledBorder("FORO"));		
		panelMostrar.setPreferredSize(new Dimension(650, 450));
		scrollMostrar = new JScrollPane();

		// AQUI DEBE CARGAR LA LOGICA PARA VER LAS CARPETAS Y NAVEGAR ENTRE ELLAS
		// CUANDO LE DAS CLICK EN ACTUALIZAR ES COMO CUANDO SE REGRESABA AL MENU PRINCIPAL
		
		panelMostrar.add(scrollMostrar);
		c.add(panelMostrar);

		// -----------------------------------------------------------------------
		// 					BOTON DE AGREGAR SELECCION AL HORARIO
		// -----------------------------------------------------------------------
		
		panelBotones = new JPanel(new GridLayout(1, 2));
		panelBotones.setPreferredSize(new Dimension(650, 40));
		btnAgregar = new JButton("Agregar");
		btnActualizar = new JButton("Actualizar");
		btnSalir = new JButton("Cerrar sesion");
		
		btnAgregar.setPreferredSize(new Dimension(100, 35));
		btnAgregar.addActionListener(this);

		btnActualizar.setPreferredSize(new Dimension(100, 35));
		btnActualizar.addActionListener(this);

		btnSalir.setPreferredSize(new Dimension(100, 35));
		btnSalir.addActionListener(this);
		
		panelBotones.add(btnAgregar); panelBotones.add(btnActualizar);
		panelBotones.add(btnSalir);

		c.add(panelBotones);
		
	}

	// Se ocupa para cerrar sesion
	public void crearLogin() {
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
			// Obtiene el texto del textField y muestra solo el tema que se pide
			// Es decir, actualiza el panel de foro, si no hay resultados, mostrar: NO EXISTEN TEMAS 
		}
		else if(b == btnAgregar) {
			// Despliega pantalla para AGREGAR NUEVO POST
			// Actualiza el PANEL FORO, es decir después de agregar, debe regresar al inicio
			// Y se debe ver que ya agrego el nuevo POST
		}
		else if(b == btnActualizar) {
			// Debe actualizar el foro, es decir volver a cargar
		}
		else if(b == btnSalir) {
			// Envia a login crearLogin()
		}
	}
	public static void main(String[] args) {
		inicioV f = new inicioV();
		f.setTitle("Foro");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 720);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}