package strings.basics;

public class StringComparison {

	public static void main(String[] args) {
		String first = "Java";
		String second = "Java";
		String third = new String("Java");

		// == compares memory reference.
		System.out.println("first == second: " + (first == second));
		System.out.println("first == third: " + (first == third));

		// equals() compares actual content.
		System.out.println("first.equals(third): " + first.equals(third));
		System.out.println("Compare ignoring case: " + first.equalsIgnoreCase("java"));
	}

}
