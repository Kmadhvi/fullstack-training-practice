package exceptionhandling.basics;

public class MultipleCatchDemo {

	public static void main(String[] args) {
		try {
			String text = null;
			System.out.println(text.length());

			int[] numbers = {1, 2, 3};
			System.out.println(numbers[5]);
		} catch (NullPointerException exception) {
			System.out.println("NullPointerException handled: " + exception.getMessage());
		} catch (ArrayIndexOutOfBoundsException exception) {
			System.out.println("ArrayIndexOutOfBoundsException handled: " + exception.getMessage());
		}
	}

}
