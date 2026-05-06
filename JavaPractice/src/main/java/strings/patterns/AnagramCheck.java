package strings.patterns;

import java.util.Arrays;

public class AnagramCheck {

	public static void main(String[] args) {
		String first = "listen";
		String second = "silent";

		char[] firstArray = first.toCharArray();
		char[] secondArray = second.toCharArray();

		Arrays.sort(firstArray);
		Arrays.sort(secondArray);

		if (Arrays.equals(firstArray, secondArray)) {
			System.out.println(first + " and " + second + " are anagrams.");
		} else {
			System.out.println(first + " and " + second + " are not anagrams.");
		}
	}

}
