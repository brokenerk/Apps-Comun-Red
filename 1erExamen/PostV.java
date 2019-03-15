import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import javax.swing.table.*;

public class PostV extends JFrame implements ActionListener {
	JPanel panelInfo, panelComentario, panelAgregar, panelTexto, panelFoto, panelTotal;
	JPanel[] pComentario, pTexto, pImagen;
	JButton btnAgregar, btnBuscar, btnRegresar;
	JLabel lfoto, lNombre, nombre;
	JTextField tfBuscar;
	JTextArea taAgregar;
	JScrollPane scrollMostrar, scrollAgregar;
	ImageIcon imagen, imag;
	ImageIcon[] imagenComentario;
	JTextArea[] textComentario;
	JScrollPane[] scrollComentario;
	JLabel[] limagen;
	
	public PostV() {
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		// --------------------------------
		// 		PANEL DE LA INFORMACION
		// --------------------------------

		panelInfo = new JPanel(new GridLayout(1, 2));
		panelInfo.setPreferredSize(new Dimension(300, 20));

		lNombre = new JLabel("NOMBRE DEL POST: ");
		//nombre = new JLabel(alumno.getNombreCompleto());
		nombre = new JLabel("AMOR");
		panelInfo.add(lNombre); panelInfo.add(nombre);

		c.add(panelInfo);

		// --------------------------------
		// 	 PANEL PARA MOSTRAR LOS POST
		// --------------------------------
				
		// Se debe obtener consultando a la bd
		int numComentarios = 5;
		int i, tamPanel;
		String nombreComentario;

		// Para que se vea nice
		tamPanel = numComentarios * 120 ;
		// Panel general
		panelComentario = new JPanel(new GridLayout(numComentarios, 1));
		panelComentario.setBorder(BorderFactory.createTitledBorder("COMENTARIOS"));		
		panelComentario.setPreferredSize(new Dimension(620, tamPanel));
		scrollMostrar = new JScrollPane(panelComentario);
		//panelComentario.add(scrollMostrar);

		scrollMostrar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollMostrar.setBounds(15, 30, 625, 360);

		// Voy a crear numComentarios de paneles
		// Si tengo 5 comentarios, entonces 5 paneles
		pComentario = new JPanel[numComentarios];
		pImagen = new JPanel[numComentarios];
		pTexto = new JPanel[numComentarios];

		// Crea nunComentarios de 
		limagen = new JLabel[numComentarios];
		textComentario = new JTextArea[numComentarios];
		scrollComentario = new JScrollPane[numComentarios];
	
		for(i = 0; i < numComentarios; i++) {
			pComentario[i] = new JPanel(new GridLayout(1, 2));
			nombreComentario = "Comentario " + i;
			pComentario[i].setBorder(BorderFactory.createTitledBorder(nombreComentario));
			pComentario[i].setMinimumSize(new Dimension(555, 100));
			pComentario[i].setPreferredSize(new Dimension(555, 400)); 
			pComentario[i].setMaximumSize(new Dimension(555, 100));

			// ---------------------------
			//	PANEL TEXTO
			// ---------------------------
			pTexto[i] = new JPanel(new GridLayout(1,1));
			textComentario[i] = new JTextArea("AQUI SE VA A VER EL TEXTO");
			scrollComentario[i] = new JScrollPane(textComentario[i]);
			scrollComentario[i].setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			pTexto[i].setPreferredSize(new Dimension(400, 80));

			pTexto[i].add(scrollComentario[i]);
			
			// ---------------------------
			//	PANEL IMAGEN
			// ---------------------------
			pImagen[i] = new JPanel(new GridLayout(1, 1));
			pImagen[i].setPreferredSize(new Dimension(100, 300)); 
			
			// Agregamos la imagen
			limagen[i] = new JLabel(imag);
			//lfoto.setIcon(new ImageIcon(alumno.getFoto().getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			limagen[i].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("avatars/default.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
			
			pImagen[i].add(limagen[i]);
			// Cada panel tiene el comentario y la foto

			panelComentario.add(pTexto[i]);
			panelComentario.add(pImagen[i]);
		}

		panelTotal = new JPanel(null);
        panelTotal.setPreferredSize(new Dimension(670, 400));
        panelTotal.add(scrollMostrar);
		c.add(panelTotal);
		
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
		PostV f = new PostV();
		f.setTitle("COMENTARIOS");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(700, 725);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}