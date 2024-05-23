package app.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBase {
	
	public static Connection getConnection() throws ClassNotFoundException {
		Connection connection = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:./database/my_dictionary_translate.db");
			System.out.println("Connection Data Base Sucess!");
		} catch(SQLException exc) {
			System.out.println("Error Connection Data Base! " + exc.getMessage());
			exc.printStackTrace();
		}
		
		return connection;
	}
	
}