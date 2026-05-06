package strings.interview;

public class WordReverse {

	public static void main(String[] args) {
		String input = "Java is powerful";
		String[] words = input.split(" ");
		StringBuilder result = new StringBuilder();

		for (int i = words.length - 1; i >= 0; i--) {
			result.append(words[i]).append(" ");
		}

		System.out.println("Original Sentence: " + input);
		System.out.println("Words Reversed: " + result.toString().trim());
	}

}
