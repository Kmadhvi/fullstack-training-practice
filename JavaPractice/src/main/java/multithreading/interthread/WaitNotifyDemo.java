package multithreading.interthread;

class SharedResource {
	synchronized void waitMethod() {
		System.out.println("Thread is waiting...");

		try {
			wait();
		} catch (InterruptedException exception) {
			System.out.println("Wait interrupted: " + exception.getMessage());
		}

		System.out.println("Thread resumed after notify.");
	}

	synchronized void notifyMethod() {
		System.out.println("Thread is sending notify signal.");
		notify();
	}
}

public class WaitNotifyDemo {

	public static void main(String[] args) {
		SharedResource resource = new SharedResource();

		Thread waitingThread = new Thread(resource::waitMethod);
		Thread notifyingThread = new Thread(() -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException exception) {
				System.out.println("Notifier interrupted: " + exception.getMessage());
			}

			resource.notifyMethod();
		});

		waitingThread.start();
		notifyingThread.start();
	}

}
