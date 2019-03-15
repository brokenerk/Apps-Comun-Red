import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import java.util.ArrayList;

public class ForoV extends JFrame implements ActionListener {
	JPanel panelInfo, panelAvatar, panelDatos, panelBuscar, panelMostrar,  panelBotones;
	JButton btnBuscar, btnAgregar, btnActualizar, btnSalir;
	JLabel lAvatar, lNickname, nickname, lId, id;
	JTextField tfBuscar;
	JScrollPane scrollMostrar;
	Usuario usuario;
	ImageIcon avatar;
	JTree tree;
	DefaultTreeModel modeloTree;
	
	public ForoV(Usuario usuario) {
		//Obtenemos el usuario logeado (sesion)
		this.usuario = usuario;
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		// -----------------------------------------------------------------------
		// 						PANEL DE avatar
		// -----------------------------------------------------------------------

		// Agregamos al panel DATOS la información del usuario
		panelAvatar = new JPanel(new GridLayout(1, 1));
		panelAvatar.setPreferredSize(new Dimension(100, 100));

		// Agregamos al panel avatar la imagen default
		lAvatar = new JLabel(avatar);
		lAvatar.setIcon(new ImageIcon(usuario.getAvatar().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		panelAvatar.add(lAvatar);
		
		// -----------------------------------------------------------------------
		// 						PANEL DE DATOS PERSONALES
		// -----------------------------------------------------------------------

		// Agregamos al panel DATOS la información del usuario
		panelDatos = new JPanel(new GridLayout(2, 2));
		panelDatos.setBorder(BorderFactory.createTitledBorder("Sesion del Usuario"));		
		panelDatos.setPreferredSize(new Dimension(300, 100));

		lId = new JLabel("Id: ");
		id = new JLabel("" + usuario.getId());
		panelDatos.add(lId); panelDatos.add(id);

		lNickname = new JLabel("Nickname: ");
		nickname = new JLabel(usuario.getNickname());
		panelDatos.add(lNickname); panelDatos.add(nickname);

		// -----------------------------------------------------------------------
		// 				PANEL QUE INTEGRA avatar Y DATOS PERSONALES
		// -----------------------------------------------------------------------
		
		panelInfo = new JPanel(new GridLayout(1, 2));
		panelInfo.setPreferredSize(new Dimension(650, 100));
		panelInfo.add(panelAvatar); panelInfo.add(panelDatos);
		c.add(panelInfo);

		// -----------------------------------------------------------------------
		// 						PANEL PARA BUSCAR 
		// -----------------------------------------------------------------------
		
		// Agregamos al panel GRUPO el comboBox para elegir un grupo
		panelBuscar = new JPanel(new GridLayout(1,2));
		panelBuscar.setBorder(BorderFactory.createTitledBorder("Buscar"));		
		panelBuscar.setPreferredSize(new Dimension(650, 50));

		tfBuscar = new JTextField();
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(this);
		panelBuscar.add(tfBuscar); panelBuscar.add(btnBuscar);
		
		c.add(panelBuscar);

		// -----------------------------------------------------------------------
		// 				PANEL PARA MOSTRAR LAS PUBLICACIONES: JTree
		// -----------------------------------------------------------------------
		
		panelMostrar = new JPanel(new BorderLayout());
		panelMostrar.setBorder(BorderFactory.createTitledBorder("Foro"));		
		panelMostrar.setPreferredSize(new Dimension(650, 450));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Foro");
        //DefaultMutableTreeNode vegetableNode = new DefaultMutableTreeNode("Vegetables");
        //DefaultMutableTreeNode fruitNode = new DefaultMutableTreeNode("Fruits");

        //root.add(vegetableNode);
        //root.add(fruitNode);

        tree = new JTree(root);
        scrollMostrar = new JScrollPane(tree);
        tree.setRootVisible(false);

		panelMostrar.add(BorderLayout.CENTER, scrollMostrar);
		c.add(panelMostrar);

		//Cargamos los posts desde el servidor
		modeloTree = (DefaultTreeModel)tree.getModel();
		Cliente.actualizar(modeloTree);

		// -----------------------------------------------------------------------
		// 					BOTON DE AGREGAR SELECCION AL HORARIO
		// -----------------------------------------------------------------------
		
		panelBotones = new JPanel(new GridLayout(1, 2));
		panelBotones.setPreferredSize(new Dimension(650, 40));
		btnAgregar = new JButton("Nuevo Post");
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
			modeloTree = (DefaultTreeModel)tree.getModel();
			Cliente.actualizar(modeloTree);
		}
		else if(b == btnSalir) {
			//Cerrar sesion, abrir login
			System.out.print("Abriendo Login....");
			crearLogin();
			System.out.print("Cerrando ForoV....");
			this.setVisible(false);
			System.out.println(" Cerrado.");
			usuario = null;
			this.dispose();
		}
	}
	public static void main(String[] args) {
		ForoV f = new ForoV(null);
		f.setTitle("Foro");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 720);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}