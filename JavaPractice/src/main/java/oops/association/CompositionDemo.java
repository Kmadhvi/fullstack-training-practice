package oops.association;

class Engine {
	void startEngine() {
		System.out.println("Engine is starting.");
	}
}

class VehicleComposition {
	private final Engine engine;

	VehicleComposition() {
		// In composition, the inner object is created inside the outer object.
		engine = new Engine();
	}

	void drive() {
		engine.startEngine();
		System.out.println("Vehicle is moving.");
	}
}

public class CompositionDemo {

	public static void main(String[] args) {
		VehicleComposition vehicle = new VehicleComposition();
		vehicle.drive();
	}

}
