package strings.manipulation;

public class RemoveSpaces {

	public static void main(String[] args) {
		String input = "Java is easy to learn";
		String result = input.replace(" ", "");

		System.out.println("Original String: " + input);
		System.out.println("Without Spaces: " + result);
	}

}
