package collections.list;

import java.util.LinkedList;

public class LinkedListDemo {

	public static void main(String[] args) {
		// LinkedList allows insertion order and can also store null values.
		LinkedList<String> names = new LinkedList<>();

		names.add("Meena");
		names.add("Yamik");
		names.add("Neha");
		names.add(null);
		names.add(null);

		System.out.println("LinkedList elements: " + names);

		names.addFirst("Riya");
		names.addLast("Hiya");

		System.out.println("After adding first and last: " + names);
		System.out.println("First element: " + names.getFirst());
		System.out.println("Last element: " + names.getLast());

		names.removeFirst();
		names.removeLast();

		System.out.println("After removing first and last: " + names);
	}

}
