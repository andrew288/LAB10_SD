import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConeccionMYSQL {
	
	private String dbUrl = "jdbc:mysql://localhost:3306/EMPRESA_EPIS";
	private String dbUser = "root";
	private String dbPassword = "";
	private String jdbcName = "com.mysql.cj.jdbc.Driver";
	
	public Connection getConn() {
		Connection conn = null;
		try {
			Class.forName(jdbcName);
		} catch (Exception e ) {
			System.out.println("¡Error al cargar el controlador!");
		}
		try {
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
		} catch (SQLException ex) {
			System.out.println("¡Error al conectarse a la base de datos!");
		}
		System.out.println("Se conecto");
		return conn;
	}	
}

