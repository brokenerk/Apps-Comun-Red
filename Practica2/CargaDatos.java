import java.util.*;

public class CargaDatos{
	public static void construyeObjetos(Materia[] materias, String[] horas, String[] prof, Grupo[] grupo){

		/*Se cargan 10 materias*/
		materias[0] = new Materia(0, "Quimica VI");
		materias[1] = new Materia(1, "Web Application Development");
		materias[2] = new Materia(2, "Instrumentacion");
		materias[3] = new Materia(3, "Ingenieria Software");
		materias[4] = new Materia(4, "Matematicas Avanzadas para la Ingenieria");
		materias[5] = new Materia(5, "Sistemas Operativos");
		materias[6] = new Materia(6, "Tecnologias para la Web");
		materias[7] = new Materia(7, "Analisis de Algoritmos");
		materias[8] = new Materia(8, "Calculo Aplicado");
		materias[9] = new Materia(9, "Aplicaciones para Comunicaciones de Red");

		/*Se cargan las horas matutinas y vespertinas*/
		horas[0] = "7:00 - 8:30";
		horas[1] = "8:30 - 10:00";
		horas[2] = "10:30 - 12:00";
		horas[3] = "12:00 - 13:30";
		horas[4] = "13:30 - 15:00";
		horas[5] = "15:00 - 16:30";
		horas[6] = "16:30 - 18:00";
		horas[7] = "18:00 - 19:30";
		horas[8] = "19:30 - 21:00";
		horas[9] = "21:00 - 22:30";


		/*Se cargan los profesores para cada materia*/
		prof[0] = "Copca Ramirez Vargas";
		prof[1] = "Hermes Francisco Montes Casiano";
		prof[2] = "Juan Carlos Tellez Barrera";
		prof[3] = "Jose Jaime Lopez Rabadan";
		prof[4] = "Ignacio Rios de la Torre";
		prof[5] = "Jorge Cortes Galicia";
		prof[6] = "Jose Antonio Ramirez";
		prof[7] = "Edgardo Adrían Franco Martínez";
		prof[8] = "Hector Rojas Luna";
		prof[9] = "Axel Ernesto Moreno Cervantes"; 

		grupo[0] = new Grupo(0, "2019-2", "3CM8");
		grupo[1] = new Grupo(1, "2019-2", "3CM9");
		grupo[2] = new Grupo(2, "2019-2", "3CV8");

		Random r = new Random();
		
		/*Llenamos los 3 grupos con materias aleatorias*/
		for(int j = 0; j < 3; j++){
			Set<Integer> generados = new HashSet<>();
			for(int i = 0; i < 6; i++){
				int aleatorio = -1;
    			boolean generado = false;

    			while (!generado) {
			        int posible = r.nextInt(9);

			        if (!generados.contains(posible)) {
			            generados.add(posible);
			            aleatorio = posible;
			            generado = true;
			        }
			    }

				grupo[j].setMaterias(materias[aleatorio], i);
				grupo[j].setProfesores(prof[aleatorio], i);

				Horas h = new Horas(); //5 dias a la semana

				Set<Integer> conjHoras = new HashSet<>();
				Set<Integer> conjDias = new HashSet<>();

				for(int l = 0; l < 3; l++){
					int a = -1;
	    			boolean g = false;
	    			int a2 = -1;
	    			boolean g2 = false;

	    			while (!g) {
	    				int p1 = 0;
	    				if(j < 2)
				        	p1 = r.nextInt(3);
				        else
				        	p1 = r.nextInt(6) + 4;

				        if (!conjHoras.contains(p1)) {
				            conjHoras.add(p1);
				            a = p1;
				            g = true;
				        }
			    	}

			    	while (!g2) {
	    				int p2 = r.nextInt(5);
				        if (!conjDias.contains(p2)) {
				            conjDias.add(p2);
				            a2 = p2;
				            g2 = true;
				        }
				    }

		    		h.setHoras(horas[a], a2);
				}
				grupo[j].setHoras(h, i);
			}
		}
	}
}