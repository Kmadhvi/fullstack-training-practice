package exceptionhandling.keywords;

public class ThrowDemo {

	public static void main(String[] args) {
		int age = 16;

		try {
			if (age < 18) {
				throw new ArithmeticException("Age must be 18 or above.");
			}

			System.out.println("Eligible to vote.");
		} catch (ArithmeticException exception) {
			System.out.println("Exception thrown manually: " + exception.getMessage());
		}
	}

}
