package jdbc.crud;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.utils.DBUtil;

public class UpdateData {

	public static void main(String[] args) {
		String query = "UPDATE students SET city = 'Surat' WHERE id = 101";

		try (Connection connection = DBUtil.getConnection();
				Statement statement = connection.createStatement()) {

			int rowsUpdated = statement.executeUpdate(query);
			System.out.println("Rows updated: " + rowsUpdated);
		} catch (SQLException exception) {
			System.out.println("Update failed: " + exception.getMessage());
		}
	}

}
