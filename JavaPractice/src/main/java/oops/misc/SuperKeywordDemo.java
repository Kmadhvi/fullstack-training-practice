package oops.misc;

class Person {
	String name;
	int age;

	Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	void showDetails() {
		System.out.println("Parent Name: " + name);
		System.out.println("Parent Age: " + age);
	}

	void showDetails(String city) {
		System.out.println("Parent City: " + city);
	}
}

class StudentSuper extends Person {
	String course;

	StudentSuper(String name, int age, String course) {
		// super() calls the parent class parameterized constructor.
		super(name, age);
		this.course = course;
	}

	void displayStudentDetails() {
		System.out.println("Student Course: " + course);

		// super accesses parent class method and variables.
		super.showDetails();
		super.showDetails("Ahmedabad");
	}
}

public class SuperKeywordDemo {

	public static void main(String[] args) {
		StudentSuper student = new StudentSuper("Hetsi", 22, "Java Full Stack");
		student.displayStudentDetails();
	}

}
