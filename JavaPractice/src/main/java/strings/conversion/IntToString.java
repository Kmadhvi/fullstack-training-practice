package strings.conversion;

public class IntToString {

	public static void main(String[] args) {
		int number = 250;
		String numberText = String.valueOf(number);

		System.out.println("Integer value: " + number);
		System.out.println("String value: " + numberText);
		System.out.println("After concatenation: " + numberText + 50);
	}

}
