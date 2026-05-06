package collections.set;

import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetDemo {

	public static void main(String[] args) {
		/* =========================================================
		   1. LINKEDHASHSET BASICS
		========================================================= */

		// LinkedHashSet keeps insertion order and removes duplicates.
		Set<String> courses = new LinkedHashSet<>();

		courses.add("Java");
		courses.add("SQL");
		courses.add("Spring");
		courses.add("Java");
		courses.add("Hibernate");

		System.out.println("LinkedHashSet elements: " + courses);

		/* =========================================================
		   2. CHECK AND REMOVE
		========================================================= */

		System.out.println("Contains Spring: " + courses.contains("Spring"));

		courses.remove("SQL");
		System.out.println("After removing SQL: " + courses);

		/* =========================================================
		   3. ITERATION IN INSERTION ORDER
		========================================================= */

		for (String course : courses) {
			System.out.println("Course: " + course);
		}
	}

}
