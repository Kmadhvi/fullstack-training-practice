package collections.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class ArrayListDemo {

	public static void main(String[] args) {
		/* =========================================================
		   1. STRING LIST OPERATIONS
		========================================================= */

		List<String> names = new ArrayList<>();
		names.add("Hetsi");
		names.add("Hi");
		names.add("Hello");
		names.add("Helly");
		names.add("Apple");
		names.add("Mango");
		names.add("Banana");

		// Print names starting with H and ending with i.
		for (String name : names) {
			if (name.startsWith("H") && name.endsWith("i")) {
				System.out.println("Filtered Name: " + name);
			}
		}

		/* =========================================================
		   2. INTEGER LIST OPERATIONS
		========================================================= */

		List<Integer> list1 = new ArrayList<>();
		list1.add(11);
		list1.add(12);
		list1.add(14);
		list1.add(16);
		list1.add(15);

		System.out.println("Original List: " + list1);

		// Insert element at a specific index.
		list1.add(2, 13);
		System.out.println("After Insertion: " + list1);

		// Find minimum and maximum values manually.
		int minVal = list1.get(0);
		int maxVal = list1.get(0);

		for (int i = 1; i < list1.size(); i++) {
			int current = list1.get(i);

			if (current < minVal) {
				minVal = current;
			}

			if (current > maxVal) {
				maxVal = current;
			}
		}

		System.out.println("Minimum Value: " + minVal);
		System.out.println("Maximum Value: " + maxVal);

		/* =========================================================
		   3. SORTING (ASCENDING & DESCENDING)
		========================================================= */

		Collections.sort(list1);
		System.out.println("Ascending Order: " + list1);

		list1.sort(Collections.reverseOrder());
		System.out.println("Descending Order: " + list1);

		/* =========================================================
		   4. REMOVE DUPLICATES
		========================================================= */

		List<Integer> listWithDuplicates = Arrays.asList(1, 2, 2, 3, 4, 4, 5);

		// Set removes duplicate values automatically.
		Set<Integer> uniqueSet = new HashSet<>(listWithDuplicates);
		System.out.println("After Removing Duplicates: " + uniqueSet);

		/* =========================================================
		   5. FREQUENCY COUNT
		========================================================= */

		Map<Integer, Integer> frequencyMap = new HashMap<>();

		for (Integer num : listWithDuplicates) {
			frequencyMap.put(num, frequencyMap.getOrDefault(num, 0) + 1);
		}

		System.out.println("Frequency Count: " + frequencyMap);

		/* =========================================================
		   6. MERGE TWO LISTS
		========================================================= */

		List<Integer> list2 = Arrays.asList(100, 200, 300);

		List<Integer> mergedList = new ArrayList<>(list1);
		mergedList.addAll(list2);

		System.out.println("Merged List: " + mergedList);

		/* =========================================================
		   7. CONVERT STRINGS TO UPPERCASE
		========================================================= */

		List<String> upperCaseNames = new ArrayList<>();

		for (String name : names) {
			upperCaseNames.add(name.toUpperCase());
		}

		System.out.println("Uppercase Names: " + upperCaseNames);

		/* =========================================================
		   BONUS (INTERVIEW TIP)
		   Using Streams (Advanced Java 8+)
		========================================================= */

		List<String> streamUpper = names.stream()
				.map(String::toUpperCase)
				.toList();

		System.out.println("Stream Uppercase: " + streamUpper);

		/* =========================================================
		   8. NULL HANDLING AND TYPE CONVERSION
		========================================================= */

		List<String> streamNames = Arrays.asList(null, "Avi", "Avni", "Yamik", "Neha", "Riya", "Hiya");
		List<String> nonNullNames = streamNames.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
		System.out.println("Names without null values: " + nonNullNames);

		List<String> numberStrings = Arrays.asList("1", "2", "3");
		List<Integer> convertedNumbers = numberStrings.stream()
				.map(Integer::parseInt)
				.collect(Collectors.toList());
		System.out.println("Converted int list: " + convertedNumbers);

		/* =========================================================
		   9. STRING STREAM OPERATIONS
		========================================================= */

		streamNames.stream()
				.filter(Objects::nonNull)
				.map(String::length)
				.forEach(length -> System.out.println("Name length: " + length));

		streamNames.stream()
				.filter(Objects::nonNull)
				.map(String::toUpperCase)
				.forEach(upper -> System.out.println("Uppercase value: " + upper));

		/* =========================================================
		   10. NUMBER STREAM OPERATIONS
		========================================================= */

		List<Integer> l1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

		long evenCount = l1.stream()
				.filter(x -> x % 2 == 0)
				.count();
		System.out.println("Count of even numbers = " + evenCount);

		l1.stream()
				.filter(x -> x % 2 == 0)
				.max(Integer::compare)
				.ifPresent(x -> System.out.println("Max even number: " + x));

		Optional<Integer> maxEven = l1.stream()
				.filter(x -> x % 2 == 0)
				.max(Integer::compare);
		System.out.println("Max number " + maxEven);

		System.out.println("Distinct values squared:");
		l1.stream()
				.distinct()
				.map(x -> x * x)
				.forEach(System.out::println);

		int sumOfEvenSquares = l1.stream()
				.filter(x -> x % 2 == 0)
				.map(x -> x * x)
				.reduce(0, Integer::sum);
		System.out.println("Sum of squares of even numbers = " + sumOfEvenSquares);
	}

}
