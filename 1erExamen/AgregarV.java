import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.util.*;
import java.lang.reflect.Field;
import javax.swing.table.*;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class AgregarV extends JFrame implements ActionListener {
	JPanel panelInfo, panelAgregar, panelTexto, panelFoto;
	JButton btnAgregar, btnBuscar, btnRegresar;
	JLabel lfoto, lNombre;
	JTextField tfnombre;
	JTextArea taAgregar;
	JScrollPane scrollMostrar, scrollAgregar;
	ImageIcon imagen;
	Usuario usuario;
	Publicacion publicacion;
	File file;
	int banderaImagen = 0;

	public AgregarV(Usuario usuario) {
		//Obtenemos el usuario logeado (sesion)
		this.usuario = usuario;
		Container c = getContentPane();
		c.setLayout(new FlowLayout());

		// --------------------------------
		// 		PANEL DE LA INFORMACION
		// --------------------------------
		panelInfo = new JPanel(new GridLayout(1, 2));
		panelInfo.setPreferredSize(new Dimension(300, 20));

		lNombre = new JLabel("Nombre del Post: ");

		tfnombre = new JTextField();
		panelInfo.add(lNombre); panelInfo.add(tfnombre);

		c.add(panelInfo);

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
		lfoto.setIcon(new ImageIcon(new javax.swing.ImageIcon(getClass().getResource("./fotos/default.png")).getImage().getScaledInstance(175, 175, Image.SCALE_SMOOTH)));
		
		panelFoto.add(lfoto); 

		// ----------------------------------------
		// 	PANEL PARA AGREGAR UN NUEVO COMENTARIO
		// ----------------------------------------
		panelAgregar = new JPanel(new GridLayout(1, 2));
		panelAgregar.setBorder(BorderFactory.createTitledBorder("Agregar Comentario"));		

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

		btnBuscar = new JButton("Cargar Foto");
		btnBuscar.addActionListener(this);

		c.add(btnAgregar); c.add(btnRegresar); c.add(btnBuscar);
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
			// SI NO SE ELIGE IMAGEN, SE PONE LA DE DEFAULT O SIMPLEMENTE SE OCULTA
			String comentarioTA = taAgregar.getText();
			String nombrePub = tfnombre.getText();
			if((nombrePub != null) && (nombrePub.length() > 0)) {
				if((comentarioTA != null) && (comentarioTA.length() > 0)) {
					if(banderaImagen == 1) {
						String path = file.getAbsolutePath();
						Cliente.enviarPublicacionCompleta(usuario.getNickname(), nombrePub, comentarioTA, file, path);
					}
					else {
						Cliente.enviarPublicacion(usuario.getNickname(), nombrePub, comentarioTA);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Ingresa un comentario valido");
				}
			}
			else {
				JOptionPane.showMessageDialog(null, "Ingresa el nombre de la publicacion");
			}

			banderaImagen = 0;
			// REGRESA AL INICIO
			System.out.print("Cerrando AgregarV....");
			this.setVisible(false);
			System.out.println(" Cerrado.");
			usuario = null;
			publicacion = null;
			this.dispose();
		}
		else if(b == btnBuscar) {
			// BUSCA LA FOTO PARA AGREGARLA AL COMENTARIO
			// SUBE LA FOTO AL SERVIDOR
			seleccionarImagenFC();
		}
		else if(b == btnRegresar) {
			// REGRESA AL INICIO
			System.out.print("Cerrando AgregarV....");
			this.setVisible(false);
			System.out.println(" Cerrado.");
			usuario = null;
			publicacion = null;
			this.dispose();
		}
	}
}