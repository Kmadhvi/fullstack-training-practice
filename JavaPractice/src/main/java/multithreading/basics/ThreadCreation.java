package multithreading.basics;

class MyThread extends Thread {
	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			System.out.println("Thread using Thread class: " + i);
		}
	}
}

public class ThreadCreation {

	public static void main(String[] args) {
		// Thread is created by extending the Thread class.
		MyThread thread = new MyThread();
		thread.start();
	}

}
