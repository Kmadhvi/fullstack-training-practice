package oops.inheritance;

class Shape {
	void message() {
		System.out.println("This is a shape.");
	}
}

class Circle extends Shape {
	void drawCircle() {
		System.out.println("Drawing a circle.");
	}
}

class Rectangle extends Shape {
	void drawRectangle() {
		System.out.println("Drawing a rectangle.");
	}
}

public class HierarchicalInheritance {

	public static void main(String[] args) {
		Circle circle = new Circle();
		Rectangle rectangle = new Rectangle();

		circle.message();
		circle.drawCircle();

		rectangle.message();
		rectangle.drawRectangle();
	}

}
