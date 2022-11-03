package nebrija.gestionVideojuegos.modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBconexion {
	
	public static String JDBC_URL = "jdbc:mysql://localhost:3360";
	public static Connection instance = null;

	public static void accesoBD(String database) {
		JDBC_URL = "jdbc:mysql://localhost:3360/" + database;
	}

	public static Connection getConnection() throws SQLException {

			Properties props = new Properties();
			props.put("user", "root");
			props.put("password", "1234");
	
			
			instance = DriverManager.getConnection(JDBC_URL, props);
			

		return instance;
	}
	
	
	
}
