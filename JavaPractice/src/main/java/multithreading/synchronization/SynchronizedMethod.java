package multithreading.synchronization;

class Table {
	synchronized void printTable(int number) {
		// synchronized method allows only one thread at a time.
		for (int i = 1; i <= 5; i++) {
			System.out.println(number * i);
		}
	}
}

class TableThreadOne extends Thread {
	private final Table table;

	TableThreadOne(Table table) {
		this.table = table;
	}

	@Override
	public void run() {
		table.printTable(5);
	}
}

class TableThreadTwo extends Thread {
	private final Table table;

	TableThreadTwo(Table table) {
		this.table = table;
	}

	@Override
	public void run() {
		table.printTable(10);
	}
}

public class SynchronizedMethod {

	public static void main(String[] args) {
		Table table = new Table();

		TableThreadOne thread1 = new TableThreadOne(table);
		TableThreadTwo thread2 = new TableThreadTwo(table);

		thread1.start();
		thread2.start();
	}

}
