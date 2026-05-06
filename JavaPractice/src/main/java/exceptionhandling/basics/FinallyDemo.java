package exceptionhandling.basics;

public class FinallyDemo {

	public static void main(String[] args) {
		try {
			int value = 20 / 2;
			System.out.println("Value: " + value);
		} catch (ArithmeticException exception) {
			System.out.println("Exception caught: " + exception.getMessage());
		} finally {
			// finally block always executes whether exception occurs or not.
			System.out.println("Finally block executed.");
		}
	}

}
