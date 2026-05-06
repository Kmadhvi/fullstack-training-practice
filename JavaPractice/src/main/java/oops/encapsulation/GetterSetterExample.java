package oops.encapsulation;

public class GetterSetterExample {
	private String accountHolder;
	private double balance;

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		if (balance >= 0) {
			this.balance = balance;
		}
	}
}
