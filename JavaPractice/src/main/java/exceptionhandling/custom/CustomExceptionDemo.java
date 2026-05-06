package exceptionhandling.custom;

public class CustomExceptionDemo {

	static void validateMarks(int marks) throws CustomException {
		if (marks < 0 || marks > 100) {
			throw new CustomException("Marks should be between 0 and 100.");
		}
	}

	public static void main(String[] args) {
		try {
			validateMarks(120);
		} catch (CustomException exception) {
			System.out.println("Custom exception caught: " + exception.getMessage());
		}
	}

}
