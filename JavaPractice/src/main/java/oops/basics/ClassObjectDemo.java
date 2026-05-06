package oops.basics;

class Student {
	String name;
	int age;

	void displayDetails() {
		System.out.println("Student Name: " + name);
		System.out.println("Student Age: " + age);
	}
}

public class ClassObjectDemo {

	public static void main(String[] args) {
		// Object is created from the Student class.
		Student student1 = new Student();
		student1.name = "Rahul";
		student1.age = 21;

		student1.displayDetails();
	}

}
