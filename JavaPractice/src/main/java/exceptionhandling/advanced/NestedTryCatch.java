package exceptionhandling.advanced;

public class NestedTryCatch {

	public static void main(String[] args) {
		try {
			int[] numbers = {10, 20, 30};

			try {
				System.out.println("Division result: " + (numbers[1] / 0));
			} catch (ArithmeticException exception) {
				System.out.println("Inner catch handled: " + exception.getMessage());
			}

			System.out.println(numbers[5]);
		} catch (ArrayIndexOutOfBoundsException exception) {
			System.out.println("Outer catch handled: " + exception.getMessage());
		}
	}

}
