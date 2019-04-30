class Servidor{
	public static void main(String[] args){
		try{
			ServerSocket s = new ServerSocket(4321);
			for( ; ; ){
				Socket cl = s.accept();
				Manejador m = new Manejador(cl);
				m.start();
			}//for
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}