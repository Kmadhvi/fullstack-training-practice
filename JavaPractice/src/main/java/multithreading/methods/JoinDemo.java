package multithreading.methods;

class JoinThread extends Thread {
	@Override
	public void run() {
		for (int i = 1; i <= 3; i++) {
			System.out.println(Thread.currentThread().getName() + " running: " + i);
		}
	}
}

public class JoinDemo {

	public static void main(String[] args) {
		JoinThread first = new JoinThread();
		JoinThread second = new JoinThread();

		first.setName("First Thread");
		second.setName("Second Thread");

		first.start();

		try {
			// join() waits for one thread to finish before continuing.
			first.join();
		} catch (InterruptedException exception) {
			System.out.println("Join interrupted: " + exception.getMessage());
		}

		second.start();
	}

}
