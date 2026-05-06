package exceptionhandling.basics;

public class TryCatchDemo {

	public static void main(String[] args) {
		try {
			int result = 10 / 0;
			System.out.println("Result: " + result);
		} catch (ArithmeticException exception) {
			System.out.println("Exception caught: " + exception.getMessage());
		}
	}

}
