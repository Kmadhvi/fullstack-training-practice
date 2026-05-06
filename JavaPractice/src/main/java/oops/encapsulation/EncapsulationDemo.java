package oops.encapsulation;

public class EncapsulationDemo {

	public static void main(String[] args) {
		// Private data is accessed using public getter and setter methods.
		GetterSetterExample account = new GetterSetterExample();
		account.setAccountHolder("Priya");
		account.setBalance(25000);

		System.out.println("Account Holder: " + account.getAccountHolder());
		System.out.println("Balance: " + account.getBalance());
	}

}
