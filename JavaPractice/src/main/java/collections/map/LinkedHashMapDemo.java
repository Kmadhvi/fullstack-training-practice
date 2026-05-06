package collections.map;

import java.util.LinkedHashMap;
import java.util.Map;

public class LinkedHashMapDemo {

	public static void main(String[] args) {
		/* =========================================================
		   1. LINKEDHASHMAP BASICS
		========================================================= */

		// LinkedHashMap keeps key-value pairs in insertion order.
		Map<String, Integer> products = new LinkedHashMap<>();

		products.put("Pen", 20);
		products.put("Book", 50);
		products.put("Bag", 700);
		products.put("Bottle", 120);

		System.out.println("LinkedHashMap elements: " + products);

		/* =========================================================
		   2. ACCESS, UPDATE AND REMOVE
		========================================================= */

		System.out.println("Price of Bag: " + products.get("Bag"));

		products.put("Pen", 25);
		System.out.println("After updating Pen price: " + products);

		products.remove("Book");
		System.out.println("After removing Book: " + products);

		/* =========================================================
		   3. ITERATION IN INSERTION ORDER
		========================================================= */

		for (Map.Entry<String, Integer> entry : products.entrySet()) {
			System.out.println(entry.getKey() + " -> " + entry.getValue());
		}
	}

}
