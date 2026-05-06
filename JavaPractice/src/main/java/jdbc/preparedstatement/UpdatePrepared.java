package jdbc.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.utils.DBUtil;

public class UpdatePrepared {

	public static void main(String[] args) {
		String query = "UPDATE students SET city = ? WHERE id = ?";

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, "Mumbai");
			preparedStatement.setInt(2, 102);

			int rowsUpdated = preparedStatement.executeUpdate();
			System.out.println("Rows updated using PreparedStatement: " + rowsUpdated);
		} catch (SQLException exception) {
			System.out.println("Prepared update failed: " + exception.getMessage());
		}
	}

}
