public static void enviarHorario(String[] grupos, String[] materias, int boleta) {
		try {
			Socket cl = new Socket(host, pto);

			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //Enviar datps

			// La bandera tiene el valor de 2 = enviarHorario
			dos.writeInt(2);
			dos.flush();

			// Enviamos el numero de boleta
			dos.writeInt(boleta);
			dos.flush();
			System.out.println("Boleta enviada");
			// Enviamos los arreglos de materias y grupos seleccionados
			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(grupos);
			oos.flush();
			System.out.println("Grupos seleccionados enviados");
			oos.writeObject(materias);
			oos.flush();
			System.out.println("Materias seleccionadas enviadas");
			dos.close();
			oos.close();
			cl.close();
		}
		catch(Exception e) {
    		e.printStackTrace();
    	}//catch
	}