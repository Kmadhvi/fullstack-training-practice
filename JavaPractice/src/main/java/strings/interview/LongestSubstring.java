package strings.interview;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstring {

	public static void main(String[] args) {
		String input = "abcabcbb";
		int left = 0;
		int maxLength = 0;
		Set<Character> set = new HashSet<>();

		for (int right = 0; right < input.length(); right++) {
			while (set.contains(input.charAt(right))) {
				set.remove(input.charAt(left));
				left++;
			}

			set.add(input.charAt(right));
			maxLength = Math.max(maxLength, right - left + 1);
		}

		System.out.println("String: " + input);
		System.out.println("Longest substring length without repeating characters: " + maxLength);
	}

}
