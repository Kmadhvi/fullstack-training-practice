package multithreading.methods;

public class YieldDemo extends Thread {

	public YieldDemo(String name) {
		super(name);
	}

	@Override
	public void run() {
		for (int i = 1; i <= 3; i++) {
			System.out.println(getName() + " running: " + i);
			Thread.yield();
		}
	}

	public static void main(String[] args) {
		YieldDemo thread1 = new YieldDemo("Thread-1");
		YieldDemo thread2 = new YieldDemo("Thread-2");

		thread1.start();
		thread2.start();
	}

}
