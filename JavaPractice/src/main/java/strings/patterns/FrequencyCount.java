package strings.patterns;

import java.util.HashMap;
import java.util.Map;

public class FrequencyCount {

	public static void main(String[] args) {
		String input = "banana";
		Map<Character, Integer> frequencyMap = new HashMap<>();

		for (char ch : input.toCharArray()) {
			frequencyMap.put(ch, frequencyMap.getOrDefault(ch, 0) + 1);
		}

		System.out.println("String: " + input);
		System.out.println("Character frequency: " + frequencyMap);
	}

}
