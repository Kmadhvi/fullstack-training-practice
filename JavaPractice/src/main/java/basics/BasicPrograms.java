package basics;

public class BasicPrograms {

	public static void main(String[] args) {
		int number = 7;
		int factorial = 1;

		for (int i = 1; i <= number; i++) {
			factorial *= i;
		}

		System.out.println("Basic Java Programs");
		System.out.println("Number: " + number);
		System.out.println("Square: " + (number * number));
		System.out.println("Cube: " + (number * number * number));
		System.out.println("Factorial: " + factorial);
	}

}
