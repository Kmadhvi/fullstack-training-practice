package strings.immutability;

public class StringImmutabilityDemo {

	public static void main(String[] args) {
		String text = "Hello";

		// String does not change the old value; it creates a new object.
		text.concat(" Java");
		System.out.println("After concat without assignment: " + text);

		text = text.concat(" Java");
		System.out.println("After concat with assignment: " + text);
	}

}
