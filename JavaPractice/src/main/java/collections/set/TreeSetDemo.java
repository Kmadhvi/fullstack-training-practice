package collections.set;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetDemo {

	public static void main(String[] args) {
		/* =========================================================
		   1. TREESET BASICS
		========================================================= */

		// TreeSet stores unique elements in sorted order.
		TreeSet<Integer> marks = new TreeSet<>();

		marks.add(88);
		marks.add(65);
		marks.add(92);
		marks.add(75);
		marks.add(65);

		System.out.println("TreeSet elements: " + marks);

		/* =========================================================
		   2. FIRST, LAST AND SEARCH
		========================================================= */

		System.out.println("Lowest mark: " + marks.first());
		System.out.println("Highest mark: " + marks.last());
		System.out.println("Contains 75: " + marks.contains(75));

		/* =========================================================
		   3. SORTED NAVIGATION
		========================================================= */

		System.out.println("Values greater than 70: " + marks.tailSet(70));
		System.out.println("Values less than 90: " + marks.headSet(90));
	}

}
