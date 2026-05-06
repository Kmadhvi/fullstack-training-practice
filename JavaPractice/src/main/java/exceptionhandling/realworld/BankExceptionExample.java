package exceptionhandling.realworld;

class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException(String message) {
		super(message);
	}
}

class BankAccount {
	private double balance;

	BankAccount(double balance) {
		this.balance = balance;
	}

	void withdraw(double amount) throws InsufficientBalanceException {
		if (amount > balance) {
			throw new InsufficientBalanceException("Insufficient balance for withdrawal.");
		}

		balance -= amount;
		System.out.println("Withdrawal successful. Remaining balance: " + balance);
	}
}

public class BankExceptionExample {

	public static void main(String[] args) {
		BankAccount account = new BankAccount(5000);

		try {
			account.withdraw(7000);
		} catch (InsufficientBalanceException exception) {
			System.out.println("Bank exception handled: " + exception.getMessage());
		}
	}

}
