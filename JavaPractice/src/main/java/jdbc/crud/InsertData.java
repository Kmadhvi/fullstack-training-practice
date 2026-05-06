package jdbc.crud;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.utils.DBUtil;

public class InsertData {

	public static void main(String[] args) {
		String query = "INSERT INTO students (id, name, city) VALUES (101, 'Riya', 'Ahmedabad')";

		try (Connection connection = DBUtil.getConnection();
				Statement statement = connection.createStatement()) {

			int rowsInserted = statement.executeUpdate(query);
			System.out.println("Rows inserted: " + rowsInserted);
		} catch (SQLException exception) {
			System.out.println("Insert failed: " + exception.getMessage());
		}
	}

}
