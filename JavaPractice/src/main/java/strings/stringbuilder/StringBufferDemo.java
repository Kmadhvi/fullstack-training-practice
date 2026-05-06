package strings.stringbuilder;

public class StringBufferDemo {

	public static void main(String[] args) {
		// StringBuffer is synchronized and thread-safe.
		StringBuffer buffer = new StringBuffer("Hello");

		buffer.append(" World");
		buffer.insert(6, "Java ");
		buffer.delete(0, 6);

		System.out.println("StringBuffer value: " + buffer);
		System.out.println("Capacity: " + buffer.capacity());
	}

}
