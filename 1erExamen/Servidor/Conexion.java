//Compilar javac .:mysql-connector-java-5.1.47-bin.jar: Conexion.java
//Ejecutar java .:mysql-connector-java-5.1.47-bin.jar: Conexion
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.sql.Date;

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
		sdf = new SimpleDateFormat("dd-MM-yyyy");
		sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	}

	public void conectarBD() {
		try {
            Class.forName(driver);
            con = DriverManager.getConnection(url + db + "?useSSL=false",  user, password);
            con.setAutoCommit(false);
        }catch (Exception e) {
            System.out.println("Ocurrio un error : "+e.getMessage());
            System.exit(1);
        }
        System.out.println("La conexión se realizo sin problemas");
	}
	
	public void cerrarConexion() {
		try {
			con.close();
			System.out.println("Se cierra la conexion...");
		}catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args){
		Conexion c = new Conexion("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/", "Foro", "root", "root");
   		c.conectarBD();
		c.cerrarConexion();
	}
}