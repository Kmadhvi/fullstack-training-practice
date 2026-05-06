package oops.polymorphism;

public class MethodOverloading {

	// Same method name with different parameter lists is method overloading.
	int calculateFee(int amount) {
		return amount;
	}

	int calculateFee(int amount, int serviceCharge) {
		return amount + serviceCharge;
	}

	double calculateFee(double amount, double tax) {
		return amount + tax;
	}

	public static void main(String[] args) {
		MethodOverloading feeCalculator = new MethodOverloading();

		System.out.println("Fee with one parameter: " + feeCalculator.calculateFee(500));
		System.out.println("Fee with service charge: " + feeCalculator.calculateFee(500, 50));
		System.out.println("Fee with tax: " + feeCalculator.calculateFee(500.0, 25.5));
	}

}
