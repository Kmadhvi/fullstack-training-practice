package jdbc.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.utils.DBUtil;

public class InsertPrepared {

	public static void main(String[] args) {
		// PreparedStatement is safer and avoids SQL injection.
		String query = "INSERT INTO students (id, name, city) VALUES (?, ?, ?)";

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setInt(1, 102);
			preparedStatement.setString(2, "Avi");
			preparedStatement.setString(3, "Vadodara");

			int rowsInserted = preparedStatement.executeUpdate();
			System.out.println("Rows inserted using PreparedStatement: " + rowsInserted);
		} catch (SQLException exception) {
			System.out.println("Prepared insert failed: " + exception.getMessage());
		}
	}

}
