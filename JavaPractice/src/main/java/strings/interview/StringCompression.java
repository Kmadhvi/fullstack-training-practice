package strings.interview;

public class StringCompression {

	public static void main(String[] args) {
		String input = "aaabbccccd";
		StringBuilder compressed = new StringBuilder();
		int count = 1;

		for (int i = 1; i < input.length(); i++) {
			if (input.charAt(i) == input.charAt(i - 1)) {
				count++;
			} else {
				compressed.append(input.charAt(i - 1)).append(count);
				count = 1;
			}
		}

		compressed.append(input.charAt(input.length() - 1)).append(count);

		System.out.println("Original String: " + input);
		System.out.println("Compressed String: " + compressed);
	}

}
