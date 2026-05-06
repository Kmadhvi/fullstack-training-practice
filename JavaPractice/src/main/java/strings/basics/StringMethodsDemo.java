package strings.basics;

public class StringMethodsDemo {

	public static void main(String[] args) {
		String message = " Java Programming ";

		System.out.println("Original String: " + message);
		System.out.println("Length: " + message.length());
		System.out.println("Trimmed: " + message.trim());
		System.out.println("Uppercase: " + message.toUpperCase());
		System.out.println("Lowercase: " + message.toLowerCase());
		System.out.println("Character at index 2: " + message.charAt(2));
		System.out.println("Substring: " + message.substring(1, 5));
		System.out.println("Contains Java: " + message.contains("Java"));
		System.out.println("Replace: " + message.replace("Java", "Core Java"));
	}

}
