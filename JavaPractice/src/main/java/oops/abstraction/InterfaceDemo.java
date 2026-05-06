package oops.abstraction;

interface Printable {
	void print();
}

class Report implements Printable {
	@Override
	public void print() {
		System.out.println("Printing report details.");
	}
}

public class InterfaceDemo {

	public static void main(String[] args) {
		// Interface provides full abstraction for behavior.
		Printable printable = new Report();
		printable.print();
	}

}
