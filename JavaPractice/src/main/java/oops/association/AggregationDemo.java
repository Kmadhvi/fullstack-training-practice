package oops.association;

class Department {
	String departmentName;

	Department(String departmentName) {
		this.departmentName = departmentName;
	}
}

class Teacher {
	String teacherName;
	Department department;

	Teacher(String teacherName, Department department) {
		this.teacherName = teacherName;
		this.department = department;
	}

	void display() {
		System.out.println("Teacher Name: " + teacherName);
		System.out.println("Department: " + department.departmentName);
	}
}

public class AggregationDemo {

	public static void main(String[] args) {
		// Aggregation shows has-a relationship with independent objects.
		Department department = new Department("Computer Science");
		Teacher teacher = new Teacher("Anjali", department);
		teacher.display();
	}

}
