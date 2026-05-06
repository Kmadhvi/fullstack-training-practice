package collections.map;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapDemo {

	public static void main(String[] args) {
		/* =========================================================
		   1. TREEMAP BASICS
		========================================================= */

		// TreeMap sorts entries by key in natural order.
		TreeMap<String, Integer> employeeIds = new TreeMap<>();

		employeeIds.put("Kiran", 1003);
		employeeIds.put("Anita", 1001);
		employeeIds.put("Suresh", 1002);
		employeeIds.put("Bhavna", 1004);

		System.out.println("TreeMap elements: " + employeeIds);

		/* =========================================================
		   2. ACCESS AND SORTED FEATURES
		========================================================= */

		System.out.println("Employee id of Anita: " + employeeIds.get("Anita"));
		System.out.println("First key: " + employeeIds.firstKey());
		System.out.println("Last key: " + employeeIds.lastKey());

		/* =========================================================
		   3. NAVIGATION METHODS
		========================================================= */

		System.out.println("Keys before Kiran: " + employeeIds.headMap("Kiran"));
		System.out.println("Keys from Kiran onwards: " + employeeIds.tailMap("Kiran"));
	}

}
