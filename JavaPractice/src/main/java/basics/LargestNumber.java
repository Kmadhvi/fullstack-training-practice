package basics;

public class LargestNumber {

	public static void main(String[] args) {
		int a = 45;
		int b = 78;
		int c = 32;

		int largest = a;

		if (b > largest) {
			largest = b;
		}

		if (c > largest) {
			largest = c;
		}

		System.out.println("Numbers are: " + a + ", " + b + ", " + c);
		System.out.println("Largest number is: " + largest);

	}

}
