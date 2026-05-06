package jdbc.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jdbc.utils.DBUtil;

public class TransactionDemo {

	public static void main(String[] args) {
		String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?";
		String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?";

		try (Connection connection = DBUtil.getConnection();
				PreparedStatement debitStatement = connection.prepareStatement(debitQuery);
				PreparedStatement creditStatement = connection.prepareStatement(creditQuery)) {

			connection.setAutoCommit(false);

			debitStatement.setDouble(1, 1000);
			debitStatement.setInt(2, 1);
			debitStatement.executeUpdate();

			creditStatement.setDouble(1, 1000);
			creditStatement.setInt(2, 2);
			creditStatement.executeUpdate();

			connection.commit();
			System.out.println("Transaction committed successfully.");
		} catch (SQLException exception) {
			System.out.println("Transaction failed: " + exception.getMessage());
		}
	}

}
