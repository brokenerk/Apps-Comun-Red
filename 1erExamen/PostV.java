import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import javax.swing.table.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PostV extends JFrame implements ActionListener {
	JPanel panelInfo, panelComentario, panelAgregar, panelTexto, panelFoto, panelTotal;
	JPanel[] pComentario, pTexto, pImagen;
	JButton btnAgregar, btnBuscar, btnRegresar, btnActualizar;
	JLabel lfoto, lNombre, nombre, lId, id;
	JLabel[] lNombreComentario;
	JTextField tfBuscar;
	JTextArea taAgregar;
	JScrollPane scrollMostrar, scrollAgregar;
	ImageIcon imagen, imag;
	ImageIcon[] imagenComentario;
	JTextArea[] textComentario;
	JScrollPane[] scrollComentario;
	JLabel[] limagen;
	Publicacion publicacion;
	Usuario usuario;
	public static String sep = System.getProperty("file.separator");
	File file;
	int banderaImagen = 0, IdPublicacion;
	
	public PostV(int IdPublicacion, Usuario usuario) {
		//Obtenemos la publicacion y la sesion usuario en cuestion
		this.IdPublicacion = IdPublicacion;
		this.publicacion = Cliente.descargarComentarios(IdPublicacion);
		this.usuario = usuario;

		Container container = getContentPane();
		container.setLayout(new FlowLayout());

		// --------------------------------
		// 		PANEL DE LA INFORMACION
		// --------------------------------

		panelInfo = new JPanel(new FlowLayout());
		panelInfo.setPreferredSize(new Dimension(500, 20));

		lId = new JLabel("ID:");
		lNombre = new JLabel(" - ");

		id = new JLabel("" + publicacion.getId());
		nombre = new JLabel(publicacion.getNombre());

		panelInfo.add(lId); panelInfo.add(id);
		panelInfo.add(lNombre); panelInfo.add(nombre);
		container.add(panelInfo);

		// --------------------------------
		// 	 PANEL PARA MOSTRAR LOS POST
		// --------------------------------
		//Obtenemos los comentarios en un ArrayList
		ArrayList<Comentario> comentarios = publicacion.getComentarios();
				
		// Se debe obtener consultando a la bd
		int numComentarios = comentarios.size();
		int i, tamPanel;

		// Para que se vea nice
		tamPanel = numComentarios * 120 ;
		// Panel general
		panelComentario = new JPanel(new GridLayout(numComentarios, 1));
			
		panelComentario.setPreferredSize(new Dimension(620, tamPanel));
		scrollMostrar = new JScrollPane(panelComentario);
		scrollMostrar.setBorder(BorderFactory.createTitledBorder("Comentarios"));	
		scrollMostrar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollMostrar.setBounds(0, 30, 670, 360);

		// Voy a crear numComentarios de paneles
		pComentario = new JPanel[numComentarios];
		pImagen = new JPanel[numComentarios];
		pTexto = new JPanel[numComentarios];

		// Crea nunComentarios de 
		limagen = new JLabel[numComentarios];
		lNombreComentario = new JLabel[numComentarios];
		textComentario = new JTextArea[numComentarios];
		scrollComentario = new JScrollPane[numComentarios];

	
		for(i = 0; i < numComentarios; i++) {
			Comentario c = comentarios.get(i);

			pComentario[i] = new JPanel(new GridLayout(1, 2));
			pComentario[i].setPreferredSize(new Dimension(555, 400)); 
			pComentario[i].setMaximumSize(new Dimension(555, 100));

			// ---------------------------
			//	LABEL CABECERA COMENTARIO
			// ---------------------------
			String datos = "ID " + c.getId() + ". Fecha: " + c.getFecha() + ". Autor: " + c.getUsuario().getNickname();
			lNombreComentario[i] = new JLabel(datos);
			pComentario[i].setBorder(BorderFactory.createTitledBorder(datos));

			// ---------------------------
			//	PANEL TEXTO
			// ---------------------------
			pTexto[i] = new JPanel(new GridLayout(1,1));
			textComentario[i] = new JTextArea(c.getTexto());
			//No editable y saltos de linea automaticos
			textComentario[i].setEditable(false);
			textComentario[i].setLineWrap(true);
			textComentario[i].setWrapStyleWord(true);
			scrollComentario[i] = new JScrollPane(textComentario[i]);
			scrollComentario[i].setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			pTexto[i].add(scrollComentario[i]);
			
			// ---------------------------
			//	PANEL IMAGEN
			// ---------------------------
			pImagen[i] = new JPanel(new GridLayout(1, 1));
			// Agregamos la imagen
			limagen[i] = new JLabel(imag);
			ImageIcon img = c.getImagen();

			//Revisamos si se agrego o no una imagen en el comentario
			if(img == null)
				limagen[i].setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("./fotos/default.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
			else
				limagen[i].setIcon(new ImageIcon(img.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));

			pImagen[i].add(limagen[i]);

			// Cada panel tiene el comentario y la foto
			pComentario[i].add(pTexto[i]);
			pComentario[i].add(pImagen[i]);
			panelComentario.add(pComentario[i]);
		}

		panelTotal = new JPanel(null);
        panelTotal.setPreferredSize(new Dimension(670, 400));
        panelTotal.add(scrollMostrar);
		container.add(panelTotal);
		
		// ----------------------------------------
		// 			PANEL AGREGAR TEXTO
		// ----------------------------------------
		panelTexto = new JPanel(new GridLayout(1,1));
		taAgregar = new JTextArea();
		//Saltos de linea automaticos
		taAgregar.setLineWrap(true);
		taAgregar.setWrapStyleWord(true);
		scrollAgregar = new JScrollPane(taAgregar);
		scrollAgregar.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panelTexto.setPreferredSize(new Dimension(100, 200));

		// Dar margen interior y color al jpanel 
		panelTexto.setBorder(BorderFactory.createTitledBorder("Nuevo Comentario"));
		panelTexto.add(scrollAgregar);

		// ----------------------------------------
		// 			PANEL AGREGAR FOTO
		// ----------------------------------------
		panelFoto = new JPanel(new GridLayout(1, 1));
		panelFoto.setPreferredSize(new Dimension(300, 200));

		// Agregamos al panel FOTO la imagen default
		lfoto = new JLabel(imagen);
		lfoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("./fotos/default.png")).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		panelFoto.add(lfoto); 

		// ----------------------------------------
		// 	PANEL PARA AGREGAR UN NUEVO COMENTARIO
		// ----------------------------------------
		panelAgregar = new JPanel(new GridLayout(1, 2));
		panelAgregar.setBorder(BorderFactory.createTitledBorder("AGREGAR COMENTARIO"));		

		panelAgregar.setPreferredSize(new Dimension(670, 200));
		panelAgregar.add(panelTexto); panelAgregar.add(panelFoto);

		container.add(panelAgregar);	

		// -----------------------------------------
		//  		BOTON AGREGAR Y REGRESAR
		// -----------------------------------------
		btnAgregar = new JButton("Agregar comentario");
		btnAgregar.addActionListener(this);

		btnRegresar = new JButton("Regresar");
		btnRegresar.addActionListener(this);

		btnBuscar = new JButton("Cargar Foto");
		btnBuscar.addActionListener(this);

		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(this);

		container.add(btnAgregar); container.add(btnRegresar); container.add(btnBuscar); container.add(btnActualizar);
	}

	/***************************************************
				ACTUALIZAR POST
	****************************************************/
	public void actualizarPost(){
		PostV f = new PostV(IdPublicacion, usuario);
		f.setTitle("Comentarios");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(800, 725);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
		this.setVisible(false);
		this.dispose();
	}

	/***************************************************
				SELECCIONAR IMAGEN
	****************************************************/
	public void seleccionarImagenFC() {
		try {
			JFileChooser jf = new JFileChooser();
			//Solo imagenes
			jf.setFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "tif", "gif", "jpeg"));
			
			int r = jf.showOpenDialog(null);
			if(r == JFileChooser.APPROVE_OPTION) {
				file = jf.getSelectedFile();
				String path = file.getAbsolutePath();
				System.out.println("La ruta es: " + path);

				// Convertir el archivo a Bytes para poder mostrarlo
			    byte[] archivoBytes = null;
			    long tamanoArch = file.length(); // File size
			    archivoBytes = new byte[(int) tamanoArch];
			    try {
			      // Lee el archivo
			      FileInputStream docu = new FileInputStream(file);
			      // Inserta en un nuevo arreglo
			      int numBytes = docu.read(archivoBytes);
			      System.out.print("El archivo tiene " + numBytes + " de bytes.");
			      docu.close(); // Close
			    } 
			    catch (FileNotFoundException e) {
			      System.out.print("No se ha encontrado el archivo.");
			    } 
			    catch (IOException e) {
			      System.out.print("No se ha podido leer el archivo.");
			    }
			    // Creamos una imagen
				ImageIcon icon = new ImageIcon(archivoBytes);
				// Se muestra en el panel
				lfoto.setIcon(new ImageIcon(icon.getImage().getScaledInstance(175, 175, Image.SCALE_SMOOTH)));
				banderaImagen = 1;
			}
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();

		if(b == btnAgregar) {
			// AGREGA UN NUEVO COMENTARIO CON SU IMAGEN O SIN IMAGEN
			String comentarioTA = taAgregar.getText();
			if((comentarioTA != null) && (comentarioTA.length() > 0)) {
				if(banderaImagen == 1) {
					String path = file.getAbsolutePath();
					Cliente.enviarComentarioCompleto(usuario.getNickname(), usuario.getId(), publicacion.getId(), comentarioTA, file, path);
				}
				else {
					Cliente.enviarComentario(usuario.getId(), publicacion.getId(), comentarioTA);
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Ingresa un comentario valido");
			}
			banderaImagen = 0;

			/*Cerramos y abrimos la publicacion para actualizar y ver los nuevos comentarios*/
			actualizarPost();
		}
		else if(b == btnBuscar) {
			/* BUSCA LA FOTO PARA AGREGARLA AL COMENTARIO 
			// SUBE LA FOTO AL SERVIDOR (y al mismo tiempo guarda la ruta en la BD)*/
			seleccionarImagenFC();
		}
		else if(b == btnRegresar) {
			System.out.print("Cerrando PostV....");
			this.setVisible(false);
			System.out.println(" Cerrado.");
			usuario = null;
			publicacion = null;
			this.dispose();
		}
		else if(b == btnActualizar){
			/*Cerramos y abrimos la publicacion para actualizar y ver los nuevos comentarios*/
			actualizarPost();
		}
	}
}