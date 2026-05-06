package multithreading.lifecycle;

public class ThreadLifecycleDemo extends Thread {

	@Override
	public void run() {
		System.out.println("Thread is in running state.");
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadLifecycleDemo thread = new ThreadLifecycleDemo();

		System.out.println("State after creation: " + thread.getState());
		thread.start();
		System.out.println("State after start: " + thread.getState());

		thread.join();
		System.out.println("State after completion: " + thread.getState());
	}

}
