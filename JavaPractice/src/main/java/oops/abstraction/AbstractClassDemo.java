package oops.abstraction;

abstract class Payment {
	abstract void pay();

	void paymentMessage() {
		System.out.println("Payment is being processed.");
	}
}

class CreditCardPayment extends Payment {
	@Override
	void pay() {
		System.out.println("Payment made using credit card.");
	}
}

public class AbstractClassDemo {

	public static void main(String[] args) {
		// Abstract class can have both abstract and normal methods.
		Payment payment = new CreditCardPayment();
		payment.paymentMessage();
		payment.pay();
	}

}
