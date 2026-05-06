package oops.realworld;

class BankAccount {
	private String accountNumber;
	private String accountHolder;
	private double balance;

	BankAccount(String accountNumber, String accountHolder, double balance) {
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;
		this.balance = balance;
	}

	void deposit(double amount) {
		balance += amount;
		System.out.println(amount + " deposited successfully.");
	}

	void withdraw(double amount) {
		if (amount <= balance) {
			balance -= amount;
			System.out.println(amount + " withdrawn successfully.");
		} else {
			System.out.println("Insufficient balance.");
		}
	}

	void displayAccountDetails() {
		System.out.println("Account Number: " + accountNumber);
		System.out.println("Account Holder: " + accountHolder);
		System.out.println("Balance: " + balance);
	}
}

public class BankSystemExample {

	public static void main(String[] args) {
		// This example combines constructor, encapsulation, and object behavior.
		BankAccount account = new BankAccount("SB1001", "Riya", 10000);
		account.displayAccountDetails();
		account.deposit(2500);
		account.withdraw(4000);
		account.displayAccountDetails();
	}

}
