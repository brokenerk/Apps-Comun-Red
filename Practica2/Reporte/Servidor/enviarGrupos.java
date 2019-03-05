public static void enviarGrupos(Socket cl) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
			oos.writeObject(grupos);
			oos.flush();

			System.out.println("Grupos enviados");
			oos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}