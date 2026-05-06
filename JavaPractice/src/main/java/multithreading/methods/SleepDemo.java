package multithreading.methods;

public class SleepDemo extends Thread {

	@Override
	public void run() {
		for (int i = 1; i <= 3; i++) {
			try {
				// sleep() pauses the current thread for given milliseconds.
				Thread.sleep(1000);
			} catch (InterruptedException exception) {
				System.out.println("Thread interrupted: " + exception.getMessage());
			}

			System.out.println("SleepDemo count: " + i);
		}
	}

	public static void main(String[] args) {
		SleepDemo thread = new SleepDemo();
		thread.start();
	}

}
