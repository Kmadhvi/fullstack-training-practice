package strings.conversion;

public class StringToInt {

	public static void main(String[] args) {
		String numberText = "100";
		int number = Integer.parseInt(numberText);

		System.out.println("String value: " + numberText);
		System.out.println("Integer value: " + number);
		System.out.println("After addition: " + (number + 50));
	}

}
