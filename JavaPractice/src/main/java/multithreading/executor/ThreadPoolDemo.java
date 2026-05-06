package multithreading.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

	public static void main(String[] args) {
		// Thread pool reuses a fixed number of worker threads.
		ExecutorService threadPool = Executors.newFixedThreadPool(3);

		for (int i = 1; i <= 5; i++) {
			int taskNumber = i;
			threadPool.submit(() -> System.out.println("Running task " + taskNumber + " in " + Thread.currentThread().getName()));
		}

		threadPool.shutdown();
	}

}
