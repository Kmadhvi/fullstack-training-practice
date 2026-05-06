package oops.inheritance;

class Animal {
	void eat() {
		System.out.println("Animal is eating.");
	}
}

class Dog extends Animal {
	void bark() {
		System.out.println("Dog is barking.");
	}
}

public class SingleInheritance {

	public static void main(String[] args) {
		// Dog gets properties of Animal through inheritance.
		Dog dog = new Dog();
		dog.eat();
		dog.bark();
	}

}
