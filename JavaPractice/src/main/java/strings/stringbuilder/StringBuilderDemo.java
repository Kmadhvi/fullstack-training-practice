package strings.stringbuilder;

public class StringBuilderDemo {

	public static void main(String[] args) {
		// StringBuilder is mutable and faster for single-threaded string changes.
		StringBuilder builder = new StringBuilder("Java");

		builder.append(" Programming");
		builder.insert(5, "Core ");
		builder.replace(0, 4, "Advanced Java");

		System.out.println("StringBuilder value: " + builder);
		System.out.println("Reverse value: " + builder.reverse());
	}

}
