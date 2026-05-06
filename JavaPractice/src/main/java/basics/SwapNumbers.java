package basics;

public class SwapNumbers {

	public static void main(String[] args) {
		int firstNumber = 10;
		int secondNumber = 20;

		System.out.println("Before swapping:");
		System.out.println("First Number = " + firstNumber);
		System.out.println("Second Number = " + secondNumber);

		int temp = firstNumber;
		firstNumber = secondNumber;
		secondNumber = temp;

		System.out.println("After swapping:");
		System.out.println("First Number = " + firstNumber);
		System.out.println("Second Number = " + secondNumber);

	}

}
