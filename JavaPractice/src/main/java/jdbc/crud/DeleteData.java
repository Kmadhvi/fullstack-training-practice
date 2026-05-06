package jdbc.crud;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.utils.DBUtil;

public class DeleteData {

	public static void main(String[] args) {
		String query = "DELETE FROM students WHERE id = 101";

		try (Connection connection = DBUtil.getConnection();
				Statement statement = connection.createStatement()) {

			int rowsDeleted = statement.executeUpdate(query);
			System.out.println("Rows deleted: " + rowsDeleted);
		} catch (SQLException exception) {
			System.out.println("Delete failed: " + exception.getMessage());
		}
	}

}
