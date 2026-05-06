package collections.utilities;

import java.util.Arrays;

public class ArraysMethodsDemo {

	public static void main(String[] args) {
		/* =========================================================
           1. ARRAY DECLARATION AND INITIALIZATION
        ========================================================= */

        // Array with fixed size
        int[] array = new int[5];

        array[0] = 12;
        array[1] = 90;
        array[2] = 11;
        array[3] = 33;
        array[4] = 56;

        // Array declared and initialized together
		
		int[] arr = {11, 8, 45, 67, 29, 100};

		System.out.println("Original Array 1: " + Arrays.toString(array));
		System.out.println("Original Array 2: " + Arrays.toString(arr));

		/* =========================================================
		   2. ARRAY SEARCHING
		========================================================= */

		// Binary search works properly only on sorted arrays.
		Arrays.sort(arr);
		int position = Arrays.binarySearch(arr, 8);

		System.out.println("Sorted Array 2: " + Arrays.toString(arr));
		System.out.println("Position of 8 using binarySearch: " + position);

		/* =========================================================
		   3. ARRAY SORTING
		========================================================= */

		Arrays.sort(array);
		System.out.println("Sorted Array 1: " + Arrays.toString(array));

		/* =========================================================
		   4. ARRAY COMPARISON
		========================================================= */

		int compareResult = Arrays.compare(array, arr);
		System.out.println("Comparison of array and arr: " + compareResult);

		/* =========================================================
		   5. COPY OF ARRAY
		========================================================= */

		// Copy entire array with bigger size.
		int[] copiedArray = Arrays.copyOf(array, 11);
		System.out.println("Copy of array with size 11: " + Arrays.toString(copiedArray));

		// Copy selected elements from index 2 to index 4.
		int[] copiedRange = Arrays.copyOfRange(arr, 2, 5);
		System.out.println("Copy of range from arr: " + Arrays.toString(copiedRange));

		/* =========================================================
		   6. FILL ARRAY
		========================================================= */

		int[] fillArray = new int[5];
		Arrays.fill(fillArray, 1);
		System.out.println("Filled Array: " + Arrays.toString(fillArray));

		/* =========================================================
		   7. PARALLEL SORT
		========================================================= */

		int[] parallelSortArray = {45, 12, 89, 2, 33, 10};
		Arrays.parallelSort(parallelSortArray);
		System.out.println("Parallel Sorted Array: " + Arrays.toString(parallelSortArray));

		/* =========================================================
		   8. LINEAR SEARCH MANUALLY
		========================================================= */

		int searchKey = 56;
		boolean found = false;

		for (int value : array) {
			if (value == searchKey) {
				found = true;
				break;
			}
		}

		if (found) {
			System.out.println(searchKey + " is found in array.");
		} else {
			System.out.println(searchKey + " is not found in array.");
		}

		/* =========================================================
		   9. ARRAY LENGTH
		========================================================= */

		System.out.println("Length of array: " + array.length);
		System.out.println("Length of arr: " + arr.length);

		/* =========================================================
		   NOTES
		========================================================= */

		// 1. Arrays.binarySearch() should be used on sorted arrays.
		// 2. Arrays.copyOf() creates a new array with the given size.
		// 3. Arrays.copyOfRange() copies selected elements.
		// 4. Arrays.fill() fills all elements with one value.
		// 5. Arrays.compare() compares two arrays lexicographically.
	}

}
