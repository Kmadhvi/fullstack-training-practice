package collections.queue;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class QueueDemo {

	public static void main(String[] args) {
		// Queue follows FIFO: first in, first out.
		Queue<Integer> q2 = new LinkedList<>();
		Queue<Integer> q1 = new LinkedList<>();

		q2.offer(10);
		q2.offer(20);
		q2.offer(30);
		q2.offer(40);
		q2.offer(50);

		q1.add(10);
		q1.add(20);
		q1.add(30);
		q1.add(40);
		q1.add(50);
		q1.add(60);

		System.out.println("Queue q1: " + q1);
		System.out.println("Queue q2: " + q2);

		List<Integer> removeAllResult = new ArrayList<>(q1);
		removeAllResult.removeAll(q2);
		System.out.println("After removeAll(q2) from q1: " + removeAllResult);

		List<Integer> retainAllResult = new ArrayList<>(q1);
		retainAllResult.retainAll(q2);
		System.out.println("After retainAll(q2) from q1: " + retainAllResult);

		System.out.println("Head element using peek: " + q1.peek());
		System.out.println("Contains 60: " + q1.contains(60));

		q1.poll();
		q1.poll();
		System.out.println("After polling two elements: " + q1);
	}

}
