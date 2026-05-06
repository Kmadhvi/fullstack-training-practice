package jdbc.batch;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.utils.DBUtil;

public class BatchProcessing {

	public static void main(String[] args) {
		try (Connection connection = DBUtil.getConnection();
				Statement statement = connection.createStatement()) {

			statement.addBatch("INSERT INTO students (id, name, city) VALUES (201, 'Neha', 'Pune')");
			statement.addBatch("INSERT INTO students (id, name, city) VALUES (202, 'Ravi', 'Delhi')");
			statement.addBatch("UPDATE students SET city = 'Jaipur' WHERE id = 201");

			int[] result = statement.executeBatch();
			System.out.println("Batch executed. Total statements processed: " + result.length);
		} catch (SQLException exception) {
			System.out.println("Batch processing failed: " + exception.getMessage());
		}
	}

}
