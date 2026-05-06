package collections.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsMethodsDemo {

	public static void main(String[] args) {
		// Collections class provides utility methods for sorting and searching.
		List<Integer> numbers = new ArrayList<>();

		numbers.add(40);
		numbers.add(10);
		numbers.add(30);
		numbers.add(20);

		System.out.println("Original list: " + numbers);

		Collections.sort(numbers);
		System.out.println("Sorted list: " + numbers);

		Collections.reverse(numbers);
		System.out.println("Reversed list: " + numbers);

		Collections.shuffle(numbers);
		System.out.println("Shuffled list: " + numbers);
	}

}
