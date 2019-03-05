public static void main(String[] args) {
		// Construimos nuestros catalogos
		Catalogo.cargar();
		materias = Catalogo.getMaterias();
		grupos = Catalogo.getGrupos();
		alumnos = Catalogo.getAlumnos();

		System.out.println("Catalogos creados.");
		try {
			ServerSocket s = new ServerSocket(4321);
			s.setReuseAddress(true);
			System.out.println("Servidor Mini SAES iniciado, esperando alumnos/clientes...");

			for( ; ; ) {
				Socket cl = s.accept();
				// InputStream
				DataInputStream dis = new DataInputStream(cl.getInputStream()); 
				System.out.println("\n\nCliente conectado desde " + cl.getInetAddress() + " " + cl.getPort());

				int bandera = dis.readInt();
				// Login
				if(bandera == 0) 	
					autentificarLogin(cl, dis);
				
				// Enviar grupos con materias
				else if(bandera == 1)
					enviarGrupos(cl);
				
				// Recibir grupos y materias para horario
				else if(bandera == 2)
					recibirHorario(cl, dis);
				
				else 
					System.out.println("Error al atender la solicitud del cliente.");
				
				dis.close();
				cl.close();
			} // Fin for
		} // Fin try
		catch(Exception e) {
			e.printStackTrace();
		} // Fin catch
	}