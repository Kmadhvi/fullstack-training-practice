package jdbc.preparedstatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.utils.DBUtil;

public class DeletePrepared {

	public static void main(String[] args) {
		String query = "DELETE FROM students WHERE id = ?";

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setInt(1, 102);

			int rowsDeleted = preparedStatement.executeUpdate();
			System.out.println("Rows deleted using PreparedStatement: " + rowsDeleted);
		} catch (SQLException exception) {
			System.out.println("Prepared delete failed: " + exception.getMessage());
		}
	}

}
