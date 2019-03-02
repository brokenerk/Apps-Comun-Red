import java.awt.event.*;
import java.awt.*;
import javax.swing.*;	
import java.io.*;

public class inscribirVentana extends JFrame implements ActionListener {
	JPanel panelInfo, panelBuscar, panelMostrar, panelHorario;
	JButton btnBuscar, btnAgregar, btnInscribir;
	JLabel lGrupo, lNombre, lBoleta, nombre, boleta;
	JComboBox comboBox;
	JScrollPane scrollHorario, scrollGrupo;

	/* Para guardar el grupo que quieres ver cuando muestres
	   las materias de ese grupo.*/
	String grupo;
	// Guarda la lista de grupos que un estudiante inscribe
	static JList<String> stringGrupo;
	// Guarda la lista de materias de un grupo
	static JList<String> stringMateria;
	static DefaultListModel<String> modelo;
	
	public inscribirVentana() {
		Container c = getContentPane();
		c.setLayout(new BoxLayout(c, BoxLayout.Y_AXIS));

		/********************************************/
		/* 				PANEL INFORMACIÓN 			*/
		/********************************************/
		panelInfo = new JPanel();
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
//		panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 20, 100, 10));

		lBoleta = new JLabel("Boleta: ");
		boleta = new JLabel(" ");
		lNombre = new JLabel("Nombre: ");
		nombre = new JLabel(" ");
		
		panelInfo.add(lBoleta); panelInfo.add(boleta);
		panelInfo.add(lNombre); panelInfo.add(nombre);

		c.add(panelInfo);

		/********************************************/
		/* 			PANEL BUSCAR GRUPO 				*/
		/********************************************/
		panelBuscar = new JPanel();
		panelBuscar.setLayout(new BoxLayout(panelBuscar, BoxLayout.X_AXIS));
//		panelBuscar.setBorder(BorderFactory.createEmptyBorder(10, 60, 100, 10));

		lGrupo = new JLabel("GRUPO: ");
		comboBox = new JComboBox();
		btnBuscar = new JButton("Buscar");
		
		panelBuscar.add(lGrupo); 
		panelBuscar.add(comboBox);
		panelBuscar.add(btnBuscar);		

		c.add(panelBuscar);
		
		/********************************************/
		/* 			PANEL MOSTRAR GRUPOS 			*/
		/********************************************/
		// Se agrega sin elementos
		panelMostrar = new JPanel();
		panelMostrar.setLayout(new BoxLayout(panelMostrar, BoxLayout.PAGE_AXIS));

		grupo = new String(" ");
		stringGrupo = new JList<String>();
        stringGrupo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        /* Asi se debe llenar cuando le des click en mostarr grupos
, 		   obtienes el grupo del comboBox y entonces lo llenas   */
        /*grupo = "3CM8";
        int i;
        for(i = 0; i < 5; i++) {
        	stringGrupo.add(grupo);
        }
 		modelo = new DefaultListModel<>();
		stringGrupo.setModel(modelo);
		
		panelMostrar.add(stringGrupo);
        */
        scrollGrupo = new JScrollPane(panelMostrar);
        scrollGrupo.setMinimumSize(new Dimension(100, 200));
		
		c.add(scrollGrupo);

		/********************************************/
		/* 			PANEL MOSTRAR HORARIO 			*/
		/********************************************/		
		// Se agrega sin elementos
		panelHorario = new JPanel();
		panelHorario.setLayout(new BoxLayout(panelHorario, BoxLayout.PAGE_AXIS));

		scrollHorario = new JScrollPane(panelHorario);
        scrollHorario.setMinimumSize(new Dimension(100, 200));

		c.add(scrollHorario);

//		btnInscribir.addActionListener(this);
//		btnAgregar.addActionListener(this);	
		btnBuscar.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b == btnBuscar) {
			// Debe mostrar las	materias del grupo seleccionado
		}
/*		if(b = btnAgregar) {
			// Debe agregar las materias seleccionadas
			// Debe ir actualizando el panel para que muestre que ya agrego las materias 
		}
*/	}

	public static void main(String s[]) {
		inscribirVentana f = new inscribirVentana();
		f.setTitle("Inscribir");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(500, 200);
		f.setVisible(true);
		f.setLocationRelativeTo(null);
	}
}