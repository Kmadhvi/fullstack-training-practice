package oops.basics;

class Employee {
	String name;
	int id;

	Employee(String name, int id) {
		this.name = name;
		this.id = id;
	}

	void displayEmployee() {
		System.out.println("Employee Name: " + name);
		System.out.println("Employee Id: " + id);
	}
}

public class ConstructorDemo {

	public static void main(String[] args) {
		// Constructor initializes object values at creation time.
		Employee employee = new Employee("Neha", 101);
		employee.displayEmployee();
	}

}
