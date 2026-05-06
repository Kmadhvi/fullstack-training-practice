package jdbc.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	private static final String URL = "jdbc:mysql://localhost:3306/javapractice";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	private DBUtil() {
	}

	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USERNAME, PASSWORD);
	}

}
