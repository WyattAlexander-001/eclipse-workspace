package csi213.lab05;

import java.util.Arrays;

/**
 * This {@code FloatArrays} class provides methods for manipulating {@code Float} arrays.
 */
public class FloatArrays {

	/**
	 * Sorts the specified array using the bubble sort algorithm.
	 * 
	 * @param a
	 *            an {@code Float} array
	 */
	public static void bubbleSort(float[] a) {
		System.out.println(Arrays.toString(a));
		for (int last = a.length - 1; last >= 1; last--) {
			// TODO: add some code here

		}
		System.out.println(Arrays.toString(a));
	}

	/**
	 * Sorts the specified array using the selection sort algorithm.
	 * 
	 * @param a
	 *            an {@code Float} array
	 * @param out
	 *            a {@code PrintStream} to show the array at the end of each pass
	 */
	public static void selectionSort(float[] a) {
		System.out.println(Arrays.toString(a));
		for (int last = a.length - 1; last >= 1; last--) {
			// TODO: add some code here

		}
		System.out.println(Arrays.toString(a));
	}

	/**
	 * Sorts the specified array using the quick sort algorithm.
	 * 
	 * @param a
	 *            an {@code Float} array
	 * @param out
	 *            a {@code PrintStream} to show the array at the end of each pass
	 */
	public static void quickSort(float[] a) {
		System.out.println(Arrays.toString(a));
		for (int index = 1; index <= a.length - 1; index++) {
			// TODO: add some code here

		}
		System.out.println(Arrays.toString(a));
	}

	/**
	 * The main method of the {@code FloatArrays} class.
	 * 
	 * @param args
	 *            the program arguments
	 */
	public static void main(String[] args) {
		float[] a = { 5.3F, 3.8F, 1.2F, 2.7F, 4.99F };

		bubbleSort(Arrays.copyOf(a, a.length));
		System.out.println();

		selectionSort(Arrays.copyOf(a, a.length));
		System.out.println();

		quickSort(Arrays.copyOf(a, a.length));
		System.out.println();
	}

}
