package collections.set;

import java.util.HashSet;
import java.util.Set;

public class HashSetDemo {

	public static void main(String[] args) {
		/* =========================================================
		   1. HASHSET BASICS
		========================================================= */

		// HashSet stores unique values and does not keep insertion order.
		Set<Integer> numbers = new HashSet<>();

		numbers.add(10);
		numbers.add(20);
		numbers.add(30);
		numbers.add(20);
		numbers.add(40);

		System.out.println("HashSet elements: " + numbers);
		System.out.println("Size of HashSet: " + numbers.size());

		/* =========================================================
		   2. SEARCH AND REMOVE
		========================================================= */

		System.out.println("Contains 30: " + numbers.contains(30));

		numbers.remove(10);
		System.out.println("After removing 10: " + numbers);

		/* =========================================================
		   3. ITERATION
		========================================================= */

		for (Integer number : numbers) {
			System.out.println("Value from HashSet: " + number);
		}
	}

}
