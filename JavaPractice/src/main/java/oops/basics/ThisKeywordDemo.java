package oops.basics;

class Car {
	String model;
	int year;

	Car(String model, int year) {
		// this refers to the current object variables.
		this.model = model;
		this.year = year;
	}

	void showCar() {
		System.out.println("Car Model: " + model);
		System.out.println("Car Year: " + year);
	}
}

public class ThisKeywordDemo {

	public static void main(String[] args) {
		Car car = new Car("Honda City", 2024);
		car.showCar();
	}

}
