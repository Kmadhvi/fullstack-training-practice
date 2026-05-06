package strings.patterns;

import java.util.LinkedHashMap;
import java.util.Map;

public class FirstNonRepeatingChar {

	public static void main(String[] args) {
		String input = "swiss";
		Map<Character, Integer> frequencyMap = new LinkedHashMap<>();

		for (char ch : input.toCharArray()) {
			frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
		}

		for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
			if (entry.getValue() == 1) {
				System.out.println("First non-repeating character: " + entry.getKey());
				return;
			}
		}

		System.out.println("No non-repeating character found.");
	}

}
