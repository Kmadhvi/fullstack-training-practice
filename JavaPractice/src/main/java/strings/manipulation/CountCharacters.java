package strings.manipulation;

public class CountCharacters {

	public static void main(String[] args) {
		String input = "Java Programming";
		int count = 0;

		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) != ' ') {
				count++;
			}
		}

		System.out.println("String: " + input);
		System.out.println("Character count without spaces: " + count);
	}

}
