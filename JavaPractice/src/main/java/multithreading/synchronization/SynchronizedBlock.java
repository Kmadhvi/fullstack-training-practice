package multithreading.synchronization;

class PrintMessage {
	void print(String message) {
		synchronized (this) {
			for (int i = 0; i < 3; i++) {
				System.out.println(message);
			}
		}
	}
}

public class SynchronizedBlock {

	public static void main(String[] args) {
		PrintMessage printer = new PrintMessage();

		Thread thread1 = new Thread(() -> printer.print("Hello"));
		Thread thread2 = new Thread(() -> printer.print("Java"));

		thread1.start();
		thread2.start();
	}

}
