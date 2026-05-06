package collections.queue;

import java.util.PriorityQueue;

public class PriorityQueueDemo {

	public static void main(String[] args) {
		// PriorityQueue returns elements based on priority, not insertion order.
		PriorityQueue<Integer> pd = new PriorityQueue<>();

		pd.add(12);
		pd.add(40);
		pd.add(23);
		pd.add(56);

		System.out.println("PriorityQueue elements: " + pd);

		pd.remove();
		pd.remove();
		pd.remove();
		pd.remove();

		System.out.println("PriorityQueue after removing all elements: " + pd);
		System.out.println("Peek after empty queue: " + pd.peek());
	}

}
