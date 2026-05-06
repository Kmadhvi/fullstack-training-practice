package oops.polymorphism;

class Parent {
	void show() {
		System.out.println("This is parent class method.");
	}
}

class Child extends Parent {
	@Override
	void show() {
		System.out.println("This is child class overridden method.");
	}
}

public class MethodOverriding {

	public static void main(String[] args) {
		// Child provides its own implementation of parent method.
		Parent parent = new Child();
		parent.show();
	}

}
