import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import javax.swing.table.*;

public class AgregarV extends JFrame implements ActionListener {
	JPanel panelInfo, panelAgregar, panelTexto, panelFoto;
	JButton btnAgregar, btnBuscar, btnRegresar;
	JLabel lfoto, lNombre;
	JTextField tfnombre;
	JTextArea taAgregar;
	JScrollPane scrollMostrar, scrollAgregar;
	ImageIcon imagen;
	
	public AgregarV() {
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		// --------------------------------
		// 		PANEL DE LA INFORMACION
		// --------------------------------

		panelInfo = new JPanel(new GridLayout(1, 2));
		panelInfo.setPreferredSize(new Dimension(300, 20));

		lNombre = new JLabel("NOMBRE DEL POST: ");
		//nombre = new JLabel(alumno.getNombreCompleto());
		tfnombre = new JTextField("AMOR");
		panelInfo.add(lNombre); panelInfo.add(tfnombre);

		c.add(panelInfo);

		// ----------------------------------------
		// 			PANEL AGREGAR TEXTO
		// ----------------------------------------

		panelTexto = new JPanel(new GridLayout(1,1));
		taAgregar = new JTextArea("Escribir comentario");
		scrollAgregar = new JScrollPane(taAgregar);
		scrollAgregar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelTexto.setPreferredSize(new Dimension(100, 200));

		// Dar margen interior y color al jpanel 
		panelTexto.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.black),BorderFactory.createEmptyBorder(10, 15, 5, 15)));
		panelTexto.add(scrollAgregar);

		// ----------------------------------------
		// 			PANEL AGREGAR FOTO
		// ----------------------------------------
		
		panelFoto = new JPanel(new GridLayout(1, 1));
		panelFoto.setPreferredSize(new Dimension(300, 200));

		// Agregamos al panel FOTO la imagen default
		lfoto = new JLabel(imagen);
		//lfoto.setIcon(new ImageIcon(alumno.getFoto().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		lfoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("avatars/default.png")).getImage().getScaledInstance(175, 175, Image.SCALE_SMOOTH)));
		
		panelFoto.add(lfoto); 

		// ----------------------------------------
		// 	PANEL PARA AGREGAR UN NUEVO COMENTARIO
		// ----------------------------------------
		
		panelAgregar = new JPanel(new GridLayout(1, 2));
		panelAgregar.setBorder(BorderFactory.createTitledBorder("AGREGAR COMENTARIO"));		

		panelAgregar.setPreferredSize(new Dimension(670, 200));
		panelAgregar.add(panelTexto); panelAgregar.add(panelFoto);

		c.add(panelAgregar);	

		// -----------------------------------------
		//  		BOTON AGREGAR Y REGRESAR
		// -----------------------------------------

		btnAgregar = new JButton("Agregar comentario");
		btnAgregar.addActionListener(this);

		btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(this);

		btnBuscar = new JButton("Agregar Foto");
		btnBuscar.addActionListener(this);

		c.add(btnAgregar); c.add(btnRegresar); c.add(btnBuscar);
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();

		if(b == btnAgregar) {
			// AGREGA UN NUEVO COMENTARIO CON SU IMAGEN O SIN IMAGEN
			// SI NO SE ELIGE IMAGEN, SE PONE LA DE DEFAULT O SIMPLEMENTE SE OCULTA

		}
		else if(b == btnBuscar) {
			// BUSCA LA FOTO PARA AGREGARLA AL COMENTARIO
			// SUBE LA FOTO AL SERVIDOR
		}
		else if(b == btnRegresar) {
			// REGRESA AL INICIO
		}
	}

	public static void main(String[] args) {
		AgregarV f = new AgregarV();
		f.setTitle("Nuevo POST");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 325);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}