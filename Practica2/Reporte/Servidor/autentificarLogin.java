public static void autentificarLogin(Socket cl, DataInputStream dis) {
	try {
		// Envia y recibe objetos
		int boleta_temp = dis.readInt();
		String passwd_temp = dis.readUTF();
		System.out.println("Datos recibidos: " + boleta_temp + " " + passwd_temp + ". Buscando...");

		boolean existe = false;
		int numReg = 0;
		Alumno alumnoActual = null;

		for(int i = 0; i < alumnos.length; i++) {
			int b = alumnos[i].getBoleta();
			String p = alumnos[i].getContrasenia();

			// Para el inicio de sesión
			if(boleta_temp == b && passwd_temp.equals(p)) {
				existe = true;
				numReg = i;
			}
		}

		if(existe) {
			alumnoActual = alumnos[numReg];
			System.out.println("Objeto alumno enviado con boleta: " + alumnoActual.getBoleta());
		}
		else {
			System.out.println("Alumno no encontrado, enviando null...");
		}

		ObjectOutputStream oos = new ObjectOutputStream(cl.getOutputStream());
		oos.writeObject(alumnoActual);
		oos.flush();
		oos.close();
		// Limpiamos memoria
		alumnoActual = null;
	}
	catch(Exception e) {
		e.printStackTrace();
	} // Fin catch
}