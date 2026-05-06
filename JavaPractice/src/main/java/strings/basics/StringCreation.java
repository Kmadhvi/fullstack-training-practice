package strings.basics;

public class StringCreation {

	public static void main(String[] args) {
		// String literal is stored in the string pool.
		String name1 = "Java";

		// new keyword creates a new String object in heap memory.
		String name2 = new String("Java");

		System.out.println("String literal: " + name1);
		System.out.println("String object: " + name2);
	}

}
