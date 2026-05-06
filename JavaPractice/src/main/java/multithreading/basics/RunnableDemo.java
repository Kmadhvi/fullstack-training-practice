package multithreading.basics;

class MyRunnable implements Runnable {
	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println("Thread using Runnable interface: " + i);
		}
	}
}

public class RunnableDemo {

	public static void main(String[] args) {
		// Thread is created by implementing Runnable.
		Thread thread = new Thread(new MyRunnable());
		thread.start();
	}

}
