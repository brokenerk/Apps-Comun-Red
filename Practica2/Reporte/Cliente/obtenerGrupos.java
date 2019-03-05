public static void obtenerGrupos() {
		try {
			Socket cl = new Socket(host, pto);
 			// Enviar datos
			DataOutputStream dos = new DataOutputStream(cl.getOutputStream());

			// La bandera tiene el valor de 1 = getGrupos
			dos.writeInt(1);
			dos.flush();

			ObjectInputStream ois = new ObjectInputStream(cl.getInputStream()); //Recibir objetos

			grupos = (Grupo[]) ois.readObject();
			System.out.println("Grupos recibidos");

			ois.close();
			cl.close();

		}catch(Exception e) {
    		e.printStackTrace();
    	}//catch
	}