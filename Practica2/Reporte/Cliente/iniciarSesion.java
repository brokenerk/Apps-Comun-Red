public static Alumno iniciarSesion(int boleta_tmp, String psswd_temp) {
		Alumno alumnoActual = null;
		
		try {
			Socket cl = new Socket(host, pto);
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream()); //Enviar datos

			// Bandera con valor 0 = Login
			dos.writeInt(0);
			dos.flush();
			System.out.println("Enviando datos: " + boleta_tmp + " - " + psswd_temp + ", esperando servidor...");

			// Enviar boleta y psswd de los textbox
			dos.writeInt(boleta_tmp);
			dos.flush();
			dos.writeUTF(psswd_temp);
			dos.flush();

			// Recibir objetos
			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); 
			
			// Importante hacer un cast
			alumnoActual = (Alumno) ois.readObject();
			System.out.println("Objeto recibido");

			dos.close();
			ois.close();
			cl.close();
		}
		catch(Exception e) {
    		e.printStackTrace();
    	} // Catch

    	return alumnoActual;
	}