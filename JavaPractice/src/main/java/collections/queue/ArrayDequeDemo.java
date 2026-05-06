package collections.queue;

import java.util.ArrayDeque;

public class ArrayDequeDemo {

	public static void main(String[] args) {
		// ArrayDeque supports insertion and deletion at both ends.
		ArrayDeque<Integer> ad = new ArrayDeque<>();

		ad.addFirst(12);
		ad.addLast(33);
		ad.addFirst(45);
		ad.addLast(89);

		System.out.println("ArrayDeque elements: " + ad);
		System.out.println("First element: " + ad.peekFirst());
		System.out.println("Last element: " + ad.peekLast());

		ad.pollFirst();
		ad.pollLast();

		System.out.println("After removing first and last: " + ad);
	}

}
