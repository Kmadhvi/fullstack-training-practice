package basics;

public class StudentResult {

	public static void main(String[] args) {
		String studentName = "Rahul";
		int english = 82;
		int maths = 91;
		int science = 76;

		int total = english + maths + science;
		double average = total / 3.0;
		String result = average >= 35 ? "Pass" : "Fail";

		System.out.println("Student Name: " + studentName);
		System.out.println("English Marks: " + english);
		System.out.println("Maths Marks: " + maths);
		System.out.println("Science Marks: " + science);
		System.out.println("Total Marks: " + total);
		System.out.println("Average Marks: " + average);
		System.out.println("Result: " + result);

	}

}
