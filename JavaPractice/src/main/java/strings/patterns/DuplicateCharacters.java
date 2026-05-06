package strings.patterns;

import java.util.HashSet;
import java.util.Set;

public class DuplicateCharacters {

	public static void main(String[] args) {
		String input = "programming";
		Set<Character> seen = new HashSet<>();
		Set<Character> duplicates = new HashSet<>();

		for (char ch : input.toCharArray()) {
			if (!seen.add(ch)) {
				duplicates.add(ch);
			}
		}

		System.out.println("String: " + input);
		System.out.println("Duplicate characters: " + duplicates);
	}

}
