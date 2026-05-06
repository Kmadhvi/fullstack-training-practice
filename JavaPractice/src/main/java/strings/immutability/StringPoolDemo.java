package strings.immutability;

public class StringPoolDemo {

	public static void main(String[] args) {
		String a = "Java";
		String b = "Java";
		String c = new String("Java");

		System.out.println("a == b: " + (a == b));
		System.out.println("a == c: " + (a == c));
		System.out.println("a.equals(c): " + a.equals(c));
	}

}
