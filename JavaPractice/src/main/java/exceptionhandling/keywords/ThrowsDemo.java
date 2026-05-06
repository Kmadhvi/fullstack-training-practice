package exceptionhandling.keywords;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ThrowsDemo {

	static void readFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("sample.txt"));
		System.out.println("First line: " + reader.readLine());
		reader.close();
	}

	public static void main(String[] args) {
		try {
			readFile();
		} catch (IOException exception) {
			System.out.println("Exception handled from throws: " + exception.getMessage());
		}
	}

}
