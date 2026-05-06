package exceptionhandling.advanced;

public class ExceptionPropagation {

	static void methodOne() {
		methodTwo();
	}

	static void methodTwo() {
		methodThree();
	}

	static void methodThree() {
		int result = 50 / 0;
		System.out.println(result);
	}

	public static void main(String[] args) {
		try {
			methodOne();
		} catch (ArithmeticException exception) {
			System.out.println("Exception propagated and handled in main: " + exception.getMessage());
		}
	}

}
