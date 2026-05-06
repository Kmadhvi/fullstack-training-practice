package oops.misc;

class Counter {
	static int count = 0;

	Counter() {
		count++;
	}
}

public class StaticKeywordDemo {

	public static void main(String[] args) {
		// Static variable is shared by all objects.
		new Counter();
		new Counter();
		new Counter();

		System.out.println("Total objects created: " + Counter.count);
	}

}
