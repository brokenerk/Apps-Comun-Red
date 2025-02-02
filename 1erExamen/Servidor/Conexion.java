//Compilar: javac -cp ,;mysql-connector-java-5.1.47-bin.jar; Conexion.java
//Ejecutar: java -cp ,;mysql-connector-java-5.1.47-bin.jar; Conexion
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.ArrayList;

public class Conexion{
	String url;
	String user;
	String db;
	String driver;
	String password;
	Connection con;
	SimpleDateFormat sdf, sdf2;

	public Conexion(String driver, String url, String db, String user, String password) {
		this.driver = driver;
		this.url = url;
		this.db = db;
		this.user = user;
		this.password = password;
		this.con = null;
		sdf = new SimpleDateFormat("dd-MM-yyyy"); //Para consultas
		sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //Para insertar
	}

	public void conectarBD() {
		try {
            Class.forName(driver);
            con = DriverManager.getConnection(url + db + "?useSSL=false",  user, password);
            con.setAutoCommit(false);
        }catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
	}
	
	public void cerrarConexion() {
		try {
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public int insertarPublicacion(String nombre, java.sql.Timestamp sqlTimestamp) {
		int bandera = 1;
		try {
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO Publicacion(Nombre, Fecha) VALUES('" + nombre +"', '"+ sqlTimestamp +"');";
			stmt.executeUpdate(sql);
			con.commit();
			stmt.close();
		}
		catch(Exception e) {
			bandera = 0;
			e.printStackTrace();
		}
		return bandera;
	}
	
	public Usuario obtenerIdUsuario(String nickname_tmp){
		Usuario usuario = null;
		System.out.println("Buscando usuario en la BD.");
		try{
			Statement stmt = null;
			stmt = con.createStatement();
		    ResultSet rsUsuario = stmt.executeQuery(
								"SELECT * " +
								"FROM Usuario " +
								"WHERE Nickname = '" + nickname_tmp + "';");

		    if(rsUsuario.next()){
		    	int IdUsuario = rsUsuario.getInt("IdUsuario");
		    	String nickname = rsUsuario.getString("Nickname");
		    	String password = rsUsuario.getString("Password");
		    	String avatar = rsUsuario.getString("Avatar");
		    	usuario = new Usuario(IdUsuario, nickname, password);
		    	if(avatar != null)
		    		usuario.setAvatar(avatar);
		    }
		    rsUsuario.close();
		    stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return usuario;
	}
	public Publicacion obtenerPublicacion(String nombreP) {
		Publicacion publicacion = null;
		try{
			Statement stmt = con.createStatement();
			ResultSet rsPublicacion = stmt.executeQuery("SELECT * FROM Publicacion WHERE Nombre = '" + nombreP +"';");

		    while(rsPublicacion.next()){
		    	int IdPublicacion = rsPublicacion.getInt("IdPublicacion");
		    	String fecha = sdf.format(rsPublicacion.getDate("Fecha"));
		    	String nombre = rsPublicacion.getString("Nombre");
		    	publicacion = new Publicacion(IdPublicacion, nombre, fecha);
		    }
		    rsPublicacion.close();
		    stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return publicacion;
	}
	public Comentario obtenerComentario(Usuario usuario) {
		Comentario comentario = null;
		try {
			Statement stmt = con.createStatement();
			ResultSet rsComentario = stmt.executeQuery("SELECT * FROM Comentario order by IdComentario desc limit 1");

		    while(rsComentario.next()) {
		    	System.out.println("LA BD SI ENCONTRO UN RESULTADO");
		    	int IdComentario = rsComentario.getInt("IdComentario");
		    	String texto = rsComentario.getString("Texto");
		    	int idUser = rsComentario.getInt("IdUsuario");
		    	String fecha = sdf.format(rsComentario.getDate("Fecha"));
		    	comentario = new Comentario(IdComentario, fecha, texto, usuario);
		    }
		    rsComentario.close();
		    stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return comentario;
	}
	public int insertarComentario(java.sql.Timestamp sqlTimestamp, String texto, int idUsuario, int idPublicacion) {
		int bandera = 1;
		try {
			Statement stmt = con.createStatement();
			String sql = "INSERT INTO Comentario(Fecha, Texto, IdUsuario, IdPublicacion) VALUES('" + sqlTimestamp + "', '" + texto + "','" + idUsuario + "','" + idPublicacion + "');";
			stmt.executeUpdate(sql);
			con.commit();
			stmt.close();
		}
		catch(Exception e) {
			bandera = 0;
			e.printStackTrace();
		}
		return bandera;
	}
	public int agregarImagen(int IdComentario, String imagen) {
		int bandera = 1;
		System.out.println(imagen);
		try {
			Statement stmt = con.createStatement();
			String sql = "UPDATE Comentario SET imagen = '" + imagen + "' WHERE idComentario = " + IdComentario + ";";
			stmt.executeUpdate(sql);
			con.commit();
			stmt.close();
		}
		catch(Exception e) {
			bandera = 0;
			e.printStackTrace();
		}
		return bandera;
	}
	public Publicacion cargarComentarios(int IdPublicacion) {
		Publicacion p = null;
		try{
			Statement stmt = con.createStatement();
		    ResultSet rsPublicacion = stmt.executeQuery("SELECT Fecha, Nombre FROM Publicacion " +
		    											"WHERE IdPublicacion = " + IdPublicacion + ";");

		    if(rsPublicacion.next()){
		    	String fecha = sdf.format(rsPublicacion.getDate("Fecha"));
		    	String nombre = rsPublicacion.getString("Nombre");
		    	p = new Publicacion(IdPublicacion, nombre, fecha);

		    	Statement stmt2 = con.createStatement();
		   		ResultSet rsComentarios = stmt.executeQuery("SELECT C.*, U.Nickname from Comentario C, Usuario U " +
		   		 											"WHERE C.IdPublicacion = " + IdPublicacion + " " + 
		   		 											"AND C.IdUsuario = U.IdUsuario " +
		   		 											"ORDER BY C.Fecha ASC;");

		   		while(rsComentarios.next()){
		   			System.out.println("Comentario encontrado.");
		   			int IdComentario = rsComentarios.getInt("IdComentario");
		   			String fechaComentario = sdf.format(rsComentarios.getDate("Fecha"));
		   			String texto = rsComentarios.getString("Texto");
		   			String imagen = rsComentarios.getString("Imagen");
		   			int IdUsuario = rsComentarios.getInt("IdUsuario");
		   			String nickname = rsComentarios.getString("Nickname");

		   			Usuario u_tmp = new Usuario(IdUsuario, nickname, "");
		   			Comentario c_tmp = new Comentario(IdComentario, fechaComentario, texto, u_tmp);
		   			
		   			if(imagen != null){
		   				c_tmp.setImagen(imagen);
		   				System.out.println("Agregando imagen.");
		   			}
		   			
		   			p.setComentario(c_tmp);
		   		}
		   		rsComentarios.close();
			    stmt2.close();
			    
		    }

		    System.out.println("Comentarios recuperadas de la BD");
		    rsPublicacion.close();
		    stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return p;
	}

	public Publicacion[] recuperarPublicaciones() {
		Publicacion[] publicaciones = null;
		try{
			Statement stmt = con.createStatement();
		    ResultSet rsPublicacion = stmt.executeQuery("SELECT COUNT(*) FROM Publicacion;");

		    int cont = 0, i = 0;

		    if(rsPublicacion.next())
		    	cont = rsPublicacion.getInt(1);

		    publicaciones = new Publicacion[cont];
		    rsPublicacion = stmt.executeQuery("SELECT * FROM Publicacion ORDER BY Fecha DESC;");

		    while(rsPublicacion.next()){
		    	int IdPublicacion = rsPublicacion.getInt("IdPublicacion");
		    	String fecha = sdf.format(rsPublicacion.getDate("Fecha"));
		    	String nombre = rsPublicacion.getString("Nombre");
		    	publicaciones[i] = new Publicacion(IdPublicacion, nombre, fecha);
		    	i++;
		    }

		    System.out.println("Publicaciones recuperadas de la BD");
		    rsPublicacion.close();
		    stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return publicaciones;
	}

	public Usuario buscarUsuario(String nickname_tmp, String password_tmp){
		Usuario usuario = null;
		System.out.println("Buscando usuario en la BD.");
		try{
			Statement stmt = null;
			stmt = con.createStatement();
		    ResultSet rsUsuario = stmt.executeQuery(
								"SELECT * " +
								"FROM Usuario " +
								"WHERE Nickname = '" + nickname_tmp + "' " +
								"AND Password = '" + password_tmp + "';");

		    if(rsUsuario.next()){
		    	int IdUsuario = rsUsuario.getInt("IdUsuario");
		    	String nickname = rsUsuario.getString("Nickname");
		    	String password = rsUsuario.getString("Password");
		    	String avatar = rsUsuario.getString("Avatar");
		    	usuario = new Usuario(IdUsuario, nickname, password);

		    	if(avatar != null)
		    		usuario.setAvatar(avatar);
		    }

		    rsUsuario.close();
		    stmt.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return usuario;
	}

	public static void main(String[] args){
		Conexion c = new Conexion("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/", "foro", "root", "root");
   		c.conectarBD();
   		c.cargarComentarios(1);
		c.cerrarConexion();
	}
}