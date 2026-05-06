package multithreading.interthread;

class Company {
	private int item;
	private boolean produced = false;

	synchronized void produce(int item) {
		while (produced) {
			try {
				wait();
			} catch (InterruptedException exception) {
				System.out.println("Producer interrupted: " + exception.getMessage());
			}
		}

		this.item = item;
		produced = true;
		System.out.println("Produced: " + item);
		notify();
	}

	synchronized void consume() {
		while (!produced) {
			try {
				wait();
			} catch (InterruptedException exception) {
				System.out.println("Consumer interrupted: " + exception.getMessage());
			}
		}

		System.out.println("Consumed: " + item);
		produced = false;
		notify();
	}
}

public class ProducerConsumer {

	public static void main(String[] args) {
		Company company = new Company();

		Thread producer = new Thread(() -> {
			for (int i = 1; i <= 5; i++) {
				company.produce(i);
			}
		});

		Thread consumer = new Thread(() -> {
			for (int i = 1; i <= 5; i++) {
				company.consume();
			}
		});

		producer.start();
		consumer.start();
	}

}
