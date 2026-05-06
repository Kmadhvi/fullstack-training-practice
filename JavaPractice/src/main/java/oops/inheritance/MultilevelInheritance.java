package oops.inheritance;

class Vehicle {
	void start() {
		System.out.println("Vehicle is starting.");
	}
}

class Bike extends Vehicle {
	void ride() {
		System.out.println("Bike is running on road.");
	}
}

class SportsBike extends Bike {
	void speed() {
		System.out.println("Sports bike runs at high speed.");
	}
}

public class MultilevelInheritance {

	public static void main(String[] args) {
		SportsBike sportsBike = new SportsBike();
		sportsBike.start();
		sportsBike.ride();
		sportsBike.speed();
	}

}
