package exceptionhandling.advanced;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TryWithResources {

	public static void main(String[] args) {
		// Resources are closed automatically after execution.
		try (BufferedReader reader = new BufferedReader(new FileReader("sample.txt"))) {
			System.out.println("File content: " + reader.readLine());
		} catch (IOException exception) {
			System.out.println("Exception in try-with-resources: " + exception.getMessage());
		}
	}

}
