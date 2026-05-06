package oops.misc;

final class Constants {
	final double PI = 3.14159;

	void displayValue() {
		System.out.println("Value of PI: " + PI);
	}
}

public class FinalKeywordDemo {

	public static void main(String[] args) {
		// final keyword can be used with class, variable, and method.
		Constants constants = new Constants();
		constants.displayValue();
	}

}
