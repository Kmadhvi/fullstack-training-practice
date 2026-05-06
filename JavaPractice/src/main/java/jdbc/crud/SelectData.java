package jdbc.crud;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jdbc.utils.DBUtil;

public class SelectData {

	public static void main(String[] args) {
		String query = "SELECT id, name, city FROM students";

		try (Connection connection = DBUtil.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query)) {

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String city = resultSet.getString("city");

				System.out.println("Id: " + id + ", Name: " + name + ", City: " + city);
			}
		} catch (SQLException exception) {
			System.out.println("Select failed: " + exception.getMessage());
		}
	}

}
