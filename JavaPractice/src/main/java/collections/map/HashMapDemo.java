package collections.map;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {

	public static void main(String[] args) {
		/* =========================================================
		   1. HASHMAP BASICS
		========================================================= */

		// HashMap stores data as key-value pairs and does not keep order.
		Map<Integer, String> students = new HashMap<>();

		students.put(101, "Amit");
		students.put(102, "Neha");
		students.put(103, "Ravi");
		students.put(104, "Pooja");

		System.out.println("HashMap elements: " + students);

		/* =========================================================
		   2. ACCESS AND UPDATE
		========================================================= */

		System.out.println("Student with id 102: " + students.get(102));

		students.put(102, "Priya");
		System.out.println("After updating key 102: " + students);

		/* =========================================================
		   3. SEARCH AND REMOVE
		========================================================= */

		System.out.println("Contains key 103: " + students.containsKey(103));
		System.out.println("Contains value Amit: " + students.containsValue("Amit"));

		students.remove(101);
		System.out.println("After removing key 101: " + students);

		/* =========================================================
		   4. ITERATION
		========================================================= */

		for (Map.Entry<Integer, String> entry : students.entrySet()) {
			System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
		}
	}

}
