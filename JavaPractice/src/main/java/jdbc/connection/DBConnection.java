package jdbc.connection;

import java.sql.Connection;
import java.sql.SQLException;

import jdbc.utils.DBUtil;

public class DBConnection {

	public static void main(String[] args) {
		// Checks whether database connection can be established.
		try (Connection connection = DBUtil.getConnection()) {
			if (connection != null) {
				System.out.println("Database connected successfully.");
			}
		} catch (SQLException exception) {
			System.out.println("Connection failed: " + exception.getMessage());
		}
	}

}
