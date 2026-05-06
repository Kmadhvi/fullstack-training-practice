package multithreading.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceDemo {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(2);

		executorService.execute(() -> System.out.println("Task 1 executed by " + Thread.currentThread().getName()));
		executorService.execute(() -> System.out.println("Task 2 executed by " + Thread.currentThread().getName()));
		executorService.execute(() -> System.out.println("Task 3 executed by " + Thread.currentThread().getName()));

		executorService.shutdown();
	}

}
